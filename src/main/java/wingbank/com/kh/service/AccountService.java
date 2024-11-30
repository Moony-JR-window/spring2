package wingbank.com.kh.service;

import wingbank.com.kh.dto.AccountDto;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.dto.GetAccountReq;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAccounts();
    Account createAccount(AccountDto accountDto);
    AccountDto getAccount(GetAccountReq getAccountReq);
}
