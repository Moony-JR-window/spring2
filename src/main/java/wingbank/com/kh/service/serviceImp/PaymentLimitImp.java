package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.GetAccountReq;
import wingbank.com.kh.dto.PaymentLimitDto;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.PaymentLimit;
import wingbank.com.kh.model.TransactionType;
import wingbank.com.kh.repository.AccountRepository;
import wingbank.com.kh.repository.PaymentLimitRepository;
import wingbank.com.kh.repository.TransactionTypeRepository;
import wingbank.com.kh.service.PasswordEncryption;
import wingbank.com.kh.service.PaymentLimitService;
import wingbank.com.kh.utils.CheckPassword;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PaymentLimitImp implements PaymentLimitService {

    final PaymentLimitRepository paymentLimitRepository;
    final AccountRepository accountRepository;
    final TransactionTypeRepository transactionTypeRepository;
    final PasswordEncryption passwordEncryption;
    final CheckPassword checkPassword;

    public PaymentLimitImp(PaymentLimitRepository paymentLimitRepository, AccountRepository accountRepository, TransactionTypeRepository transactionTypeRepository, PasswordEncryption passwordEncryption, CheckPassword checkPassword) {
        this.paymentLimitRepository = paymentLimitRepository;
        this.accountRepository = accountRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.passwordEncryption = passwordEncryption;
        this.checkPassword = checkPassword;
    }

    @Override
    public PaymentLimit createPaymentLimit(PaymentLimitDto paymentLimitDto) {
        try {

            // Fetch Account
            Account account = accountRepository.findById(paymentLimitDto.getAccountId())
                    .orElseThrow(() -> new CustomException("Account not found", HttpStatus.BAD_REQUEST));
            GetAccountReq getAccountReq = new GetAccountReq();
            getAccountReq.setAccountId(paymentLimitDto.getAccountId());
            getAccountReq.setPassword(paymentLimitDto.getPassword());
            boolean check = passwordEncryption.verifyPassword(getAccountReq.getPassword(), account.getPassword());
            if (!check) {
                checkPassword.checkPassword(paymentLimitDto.getAccountId());
                throw new CustomException("Invalid password", HttpStatus.BAD_REQUEST);
            }
            // Fetch TransactionType
            TransactionType transactionType = transactionTypeRepository.findById(paymentLimitDto.getTransactionTypeId())
                    .orElseThrow(() -> new CustomException("Transaction type not found", HttpStatus.BAD_REQUEST));

            // Check if a PaymentLimit exists
            PaymentLimit existingPaymentLimit = paymentLimitRepository
                    .findByAccountIdAndTransactionType(account, transactionType)
                    .orElse(null);

            if (existingPaymentLimit != null) {
                // Update the existing PaymentLimit
                existingPaymentLimit.setAccountId(account);
                existingPaymentLimit.setTransactionType(transactionType);
                existingPaymentLimit.setPinThreshold(paymentLimitDto.getPinidThreshold());
                existingPaymentLimit.setFaceidThreshold(paymentLimitDto.getFaceidThreshold());
                existingPaymentLimit.setUpdatedAt(LocalDateTime.now()); // Set an updated timestamp if needed
                PaymentLimit updatedPaymentLimit = paymentLimitRepository.save(existingPaymentLimit);

                if (updatedPaymentLimit == null) {
                    throw new CustomException("Failed to update payment limit", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return updatedPaymentLimit;
            } else {
                // Create a new PaymentLimit
                PaymentLimit newPaymentLimit = PaymentLimitDto.toEntity(paymentLimitDto, account, transactionType);
                PaymentLimit savedPaymentLimit = paymentLimitRepository.save(newPaymentLimit);

                if (savedPaymentLimit == null) {
                    throw new CustomException("Payment limit not created", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return savedPaymentLimit;
            }
        } catch (CustomException e) {
            // Re-throw the custom exception
            throw new CustomException(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            // Handle unexpected exceptions
            log.error("Unexpected error while creating/updating payment limit", e);
            throw new CustomException("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public PaymentLimit checkPaymentLimit(Account account, TransactionType transactionType) {

        PaymentLimit existingPaymentLimit = paymentLimitRepository
                .findByAccountIdAndTransactionType(account, transactionType)
                .orElse(null);
        if (existingPaymentLimit == null) {
            log.error("Payment limit not created");
            return null;
        }
        if (existingPaymentLimit.getAccountId() == account && existingPaymentLimit.getTransactionType() == transactionType) {
            log.info("Payment is okay can make logic limit {} ", true);
            return existingPaymentLimit;
        } else {
            log.info("Payment is not okay can make logic limit {} ", false);
            return null;
        }
    }


}
