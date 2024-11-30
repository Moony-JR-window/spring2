package wingbank.com.kh.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wingbank.com.kh.enums.AccountStatus;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.repository.AccountRepository;

@Getter
@Setter
@Slf4j
@Component
public class CheckPassword {
    final AccountRepository accountRepository;

    public CheckPassword(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void checkPassword(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);

        account.setWrongPassword(account.getWrongPassword() + 1);
        if (account.getWrongPassword() > 3) {
            account.setAccountStatus(AccountStatus.inactive);
        }
        accountRepository.save(account);
        log.info("====={}", account);

    }
}
