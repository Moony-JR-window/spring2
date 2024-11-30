package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.DailyTransactionLogDto;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.DailyTransactionLog;
import wingbank.com.kh.model.TransactionType;
import wingbank.com.kh.repository.DailyTransactionLogRepository;
import wingbank.com.kh.service.DailyTransactionLogService;

import java.math.BigDecimal;

@Slf4j
@Service
public class DailyTransactionLogServiceImp implements DailyTransactionLogService {

    final DailyTransactionLogRepository dailyTransactionLogRepository;

    public DailyTransactionLogServiceImp(DailyTransactionLogRepository dailyTransactionLogRepository) {
        this.dailyTransactionLogRepository = dailyTransactionLogRepository;
    }

    @Override
    public DailyTransactionLog createDailyTransactionLog(DailyTransactionLogDto dailyTransactionLogDto,
                                                         Account account,
                                                         TransactionType transactionType) {
        DailyTransactionLog dailyTransactionLog = DailyTransactionLogDto.toEntity(dailyTransactionLogDto, account, transactionType);
        return dailyTransactionLogRepository.save(dailyTransactionLog);
    }


    public DailyTransactionLog CheckValidate(Account account,TransactionType transactionType) {
        DailyTransactionLog dailyTransactionLog=dailyTransactionLogRepository
                .findByAccountIdAndTransactionTypeId(account,transactionType)
                .orElse(null);
        if(dailyTransactionLog==null){
            log.error("dailyTransactionLog is null");
            return null;
        }
        return dailyTransactionLog;
    }

    public DailyTransactionLog UpdateAmount(DailyTransactionLog dailyTransactionLog,BigDecimal TotalAmount){
        DailyTransactionLog dailyTransactionLog1=DailyTransactionLogDto.UpdateTotalAmount(dailyTransactionLog,TotalAmount);
        return dailyTransactionLogRepository.save(dailyTransactionLog1);
    }


}
