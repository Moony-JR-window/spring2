package wingbank.com.kh.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.FilterDto;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.dto.SearchTransactionDto;
import wingbank.com.kh.dto.SearchTransactionReq;
import wingbank.com.kh.service.SearchTransactionService;

import java.util.List;
@Tag(name = "Search ")
@RestController
@RequestMapping("/v1/search/transaction")
public class SearchTransactionController {

    final SearchTransactionService searchTransactionService;

    public SearchTransactionController(SearchTransactionService searchTransactionService) {
        this.searchTransactionService = searchTransactionService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search transactions populated successfully!")
    })
    @Operation(summary = "5. Clone/ Copy all Data from transaction to SearchTable",
            description = "binefit: fasting Searching all Data in Transaction  ")

    @PostMapping("/clone")
    public ResponseEntity<String> cloneTransaction() {
        searchTransactionService.cloneSearchTransactions();
        return ResponseEntity.ok("Search transactions populated successfully!");

    }

    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = SearchTransactionDto.class))}
            )
    })
    @Operation(summary = "3. Searching / filter range amount by Account",description = "Get Data filter amount by range ")

    @PostMapping
    public ResponseEntity<ReqAndRes.ResData<List<SearchTransactionDto>>> getSearchTransaction(@RequestBody FilterDto filterDto) {
        List<SearchTransactionDto> searchTransaction = searchTransactionService.getSearchTransactions(filterDto);
        return ResponseEntity.ok(new ReqAndRes.ResData<>("success",searchTransaction));

    }

    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = SearchTransactionDto.class))}
            )
    })
    @Operation(summary = "4. Searching / filter name of transaction by Account",description = "Get Data filter name type  ")

    @PostMapping("/name")
    public ResponseEntity<ReqAndRes.ResData<List<SearchTransactionDto>>> getTransactionsByType(@RequestBody SearchTransactionReq searchTransactionReq) {

        List<SearchTransactionDto> transactions = searchTransactionService.getSearchTransactionsType(searchTransactionReq);
        return ResponseEntity.ok(new ReqAndRes.ResData<>("success",transactions));
    }

}
