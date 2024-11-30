package wingbank.com.kh.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.dto.TransactionTypeDto;
import wingbank.com.kh.model.TransactionType;
import wingbank.com.kh.service.TransactionTypeSevice;

@Tag(name = "TransactionType")

@RestController
@RequestMapping("v1/transaction/type")
public class TransactionTypeController {

    final TransactionTypeSevice transactionTypeSevice;

    public TransactionTypeController(TransactionTypeSevice transactionTypeSevice) {
        this.transactionTypeSevice = transactionTypeSevice;
    }

    @PostMapping("/create")
    public ResponseEntity<ReqAndRes.ResData<TransactionType>> createTransaction(@RequestBody TransactionTypeDto transactionTypeDto) {
        TransactionType transactionType = transactionTypeSevice.createTransactionType(transactionTypeDto);
        return ResponseEntity.ok(new ReqAndRes.ResData<>("success", transactionType));
    }

}
