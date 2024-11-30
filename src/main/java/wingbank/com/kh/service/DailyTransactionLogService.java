package wingbank.com.kh.service;

import wingbank.com.kh.dto.DailyTransactionLogDto;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.DailyTransactionLog;
import wingbank.com.kh.model.TransactionType;

public interface DailyTransactionLogService {

    DailyTransactionLog createDailyTransactionLog(DailyTransactionLogDto dailyTransactionLogDto,
                                                         Account account, TransactionType transactionType);
}
