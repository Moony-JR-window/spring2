package wingbank.com.kh.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import wingbank.com.kh.enums.AccountStatus;
import wingbank.com.kh.enums.AccountType;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.Customer;
import wingbank.com.kh.model.UnlockType;
import wingbank.com.kh.service.serviceImp.PasswordEncryptioImp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Getter
@Setter
public class AccountDto {
    private Integer accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private String currency;
    private String password;
    private AccountStatus accountStatus;
    private LocalDate createdAt;
    private Integer customerId;
//    private CustomerDto customerDto;

    public static Account toEntity(AccountDto accountDto, Customer customer) {
        Account account = new Account();

        PasswordEncryptioImp passwordEncryptioImp = new PasswordEncryptioImp();
        String Encryption;
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        if (!isCurrency(accountDto.getCurrency())) {
            throw new CustomException("Currency have KHR and USD only", HttpStatus.BAD_REQUEST);
        }
        Encryption = passwordEncryptioImp.encryptPassword(accountDto.getPassword());
        log.info("=== encryption {} ", Encryption);
        account.setPassword(Encryption);
        account.setCurrency(accountDto.getCurrency());
        account.setAccountType(accountDto.getAccountType() == null ? AccountType.saving : accountDto.getAccountType());
        account.setCreatedAt(LocalDate.now());
        account.setAccountStatus(accountDto.getAccountStatus() == null ? AccountStatus.active : accountDto.getAccountStatus());
        account.setCustomer(customer);
        log.info("==============Mapper to Entity {} ", account.getCurrency());
        return account;
    }

    public static AccountDto fromEntity(Account account, Customer customer) {
        AccountDto dto = new AccountDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setPassword(account.getPassword());
        dto.setAccountType(account.getAccountType() == null ? AccountType.saving : account.getAccountType());
        dto.setCreatedAt(LocalDate.now());
        dto.setAccountStatus(account.getAccountStatus() == null ? AccountStatus.active : account.getAccountStatus());
        dto.setCustomerId(customer.getCustomerId());
        return dto;
    }

    public static Boolean isCurrency(String currency) {
        currency = currency.toUpperCase();
        if (currency.equals("KHR") || currency.equals("USD")) {
            return true;
        }
        return false;
    }
}
