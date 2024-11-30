package wingbank.com.kh.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.DailyTransactionLogDto;
import wingbank.com.kh.model.DailyTransactionLog;

@Tag(name = "Transaction Limit")
@RestController
@RequestMapping("v1/transaction/limit")
public class DailyTransactionLogController {

    @PostMapping()
    public DailyTransactionLog createDailyTransactionLog(@RequestBody DailyTransactionLogDto dailyTransactionLogDto) {
        return null;
    }

}
