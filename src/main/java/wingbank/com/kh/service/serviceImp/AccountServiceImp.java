package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.AccountDto;
import wingbank.com.kh.dto.GetAccountReq;
import wingbank.com.kh.dto.UnlockTypeDto;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.Customer;
import wingbank.com.kh.model.UnlockType;
import wingbank.com.kh.repository.AccountRepository;
import wingbank.com.kh.repository.CustomerRepository;
import wingbank.com.kh.service.AccountService;
import wingbank.com.kh.service.PasswordEncryption;
import wingbank.com.kh.service.UnlockService;
import wingbank.com.kh.utils.CheckPassword;

import java.util.List;

@Slf4j
@Service
public class AccountServiceImp implements AccountService {

    final AccountRepository accountRepository;
    final CustomerRepository customerRepository;
    final UnlockService unlockService;
    final PasswordEncryption passwordEncryption;
    final CheckPassword checkPassword;

    public AccountServiceImp(AccountRepository accountRepository, CustomerRepository customerRepository, UnlockService unlockService, PasswordEncryption passwordEncryption, CheckPassword checkPassword) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.unlockService = unlockService;
        this.passwordEncryption = passwordEncryption;
        this.checkPassword = checkPassword;
    }

    @Override
    public List<AccountDto> getAccounts() {
        return List.of();
    }

    @Override
    public Account createAccount(AccountDto accountDto) {
        try {
            Customer customer = customerRepository.findById(accountDto.getCustomerId()).orElseThrow(() -> new CustomException("Customer not found", HttpStatus.NOT_FOUND));
            Account account = AccountDto.toEntity(accountDto, customer);
            log.info("======Id dto {}", account.getAccountNumber());
            log.info("================Id acc {}", account.getAccountId());
            UnlockTypeDto unlockTypeDto = new UnlockTypeDto();
            unlockTypeDto.setUnlockTypeName("PIN");
            unlockTypeDto.setAccountId(accountDto.getCustomerId());
            accountRepository.save(account);
            UnlockType unlockType = unlockService.unlockType(unlockTypeDto, account);
            log.info("======== unlock {}", unlockType.getUnlockTypeId());
            accountRepository.updateUnlockTypeByAccountId(unlockType, account.getAccountId());

            return account;
        } catch (Exception e) {
            throw new CustomException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public AccountDto getAccount(GetAccountReq getAccountReq) {
        try {
            Account account = accountRepository.findById(getAccountReq.getAccountId())
                    .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
            Customer customer = customerRepository.findById(account.getCustomer().getCustomerId())
                    .orElseThrow(() -> new CustomException("Customer not found", HttpStatus.NOT_FOUND));
            boolean check = passwordEncryption.verifyPassword(getAccountReq.getPassword(), account.getPassword());
            if (check) {
                AccountDto accountDto = AccountDto.fromEntity(account, customer);
                return accountDto;
            }
            throw new CustomException("Password not correct", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            checkPassword.checkPassword(getAccountReq.getAccountId());
            throw new CustomException("Password not correct", HttpStatus.BAD_REQUEST);
        }
    }

}
