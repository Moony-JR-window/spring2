package wingbank.com.kh.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.AccountProfileDto;
import wingbank.com.kh.dto.GetHashing;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.dto.TransactionDto;
import wingbank.com.kh.model.Transaction;
import wingbank.com.kh.service.HashingService;
import wingbank.com.kh.service.TransactionService;

@Tag(name = "Transaction")
@RestController
@RequestMapping("v1/transaction")
public class TransactionController {

    final TransactionService transactionService;
    final HashingService hashingService;
    public TransactionController(TransactionService transactionService, HashingService hashingService) {
        this.transactionService = transactionService;
        this.hashingService = hashingService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReqAndRes.ResData<TransactionDto>> createTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto transaction = transactionService.createTransaction(transactionDto);
        return ResponseEntity.ok().body(new ReqAndRes.ResData<>("success", transaction));
    }
    @PostMapping("/hash")
    public ResponseEntity<ReqAndRes.ResData<TransactionDto>> GetTransactionByHash(@RequestBody GetHashing getHashing) {
        try {
            TransactionDto transactionDto =hashingService.getTransaction(getHashing.getHash());
            return ResponseEntity.ok().body(new ReqAndRes.ResData<>("success", transactionDto) );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<ReqAndRes.ResData<String>> checkPassword(
            @RequestBody AccountProfileDto accountProfileDto){
        try {
            transactionService.CheckPassword(accountProfileDto.getAccountNumber(),accountProfileDto.getPassword());
            return ResponseEntity.ok().body(new ReqAndRes.ResData<>("success","correct password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
