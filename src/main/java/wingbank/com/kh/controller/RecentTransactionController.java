package wingbank.com.kh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wingbank.com.kh.dto.RecentSpecificDto;
import wingbank.com.kh.dto.RecentTransactionDto;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.service.RecentTransactionService;

import java.util.List;

@Slf4j
@Tag(name = "Recent Transaction", description = "API Recent Transaction ")
@RestController
@RequestMapping("/v1/recent")
public class RecentTransactionController {


    final RecentTransactionService recentTransactionService;

    public RecentTransactionController(RecentTransactionService recentTransactionService) {
        this.recentTransactionService = recentTransactionService;
    }

    @ApiResponses({@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = RecentTransaction.class))})})
    @Operation(summary = "2. Create Recent by Id Account ", description = "API can make Recent transaction ")
    @PostMapping("/create")
    public ResponseEntity<ReqAndRes.ResData<RecentTransaction>> createRecent(@RequestBody RecentTransactionDto recentTransactionDto) {
        RecentTransaction recentTransaction = recentTransactionService.createRecentTransaction(recentTransactionDto);
        return ResponseEntity.ok(new ReqAndRes.ResData<>("success", recentTransaction));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<String> deleteRecent(@PathVariable("transactionId") int accountId) {
        String message=recentTransactionService.DeleteRecentTransaction(accountId);
        return ResponseEntity.ok("Recent transaction for accountId " + accountId + " deleted successfully");
    }

    @ApiResponses({@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = RecentTransaction.class))})})
    @Operation(summary = "1. Get Account by Id", description = "API can get Account and make pagination default: page=0,limit=10 ")
    @PostMapping
    public ResponseEntity<ReqAndRes.ResDataPage<List<RecentSpecificDto>>> getRecentTransactions(@RequestBody ReqAndRes recentTransactionReq) {

        int numPage;
        int numLimit;
        try {
            if (recentTransactionReq.getPage() == null ||
                    recentTransactionReq.getPage() == 0) {
                numPage = 0;

            } else {
                numPage = recentTransactionReq.getPage();
            }
            if (recentTransactionReq.getLimit() == null ||
                    recentTransactionReq.getLimit() == 0) {
                numLimit = 10;
            } else {
                numLimit = recentTransactionReq.getLimit();
            }

            Page<RecentSpecificDto> recentTransactions = recentTransactionService.getRecentTransactionsByAccountId(recentTransactionReq.getAccountId(), numPage, numLimit);
            log.info("====================================================================== {}",recentTransactions.getContent());
            ReqAndRes.InfoData infoData = new ReqAndRes.InfoData(recentTransactions.getPageable().getPageNumber(), recentTransactions.getPageable().getPageSize());
            return ResponseEntity.ok(new ReqAndRes.ResDataPage<>("success", recentTransactions.getContent(), infoData));
        } catch (NumberFormatException e) {
            log.info("Error Valid");
            throw new CustomException("Invalid page number", HttpStatus.BAD_REQUEST);
        }
    }

}
