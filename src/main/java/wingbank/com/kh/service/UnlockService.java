package wingbank.com.kh.service;

import wingbank.com.kh.dto.AccountDto;
import wingbank.com.kh.dto.UnlockTypeDto;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.UnlockType;

public interface UnlockService {
    UnlockType unlockType(UnlockTypeDto unlockTypeDto, Account account);
}
