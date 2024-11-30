package wingbank.com.kh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.AccountDto;
import wingbank.com.kh.dto.GetAccountReq;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.service.AccountService;

@Slf4j
@RestController
@Tag(name = "Account ", description = "API Account Check and create Account ")
@RequestMapping("/v1/account")
public class AccountController {

    final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = AccountDto.class))}
            )
    })
    @Operation(summary = "Create Account")
    @PostMapping("/create")

    public ResponseEntity<ReqAndRes.ResData<Account>> createAccount(@RequestBody AccountDto accountDto) {
        log.info(accountDto.toString());
      Account account=accountService.createAccount(accountDto);
      return ResponseEntity.ok(new ReqAndRes.ResData<>("Success", account));
   }
    @Operation(summary = "Check Balance by Account ",description = "mean check balance account when transaction or recent transaction  ")
   @PostMapping
    public ResponseEntity<ReqAndRes.ResData<AccountDto>> GetAccount(@RequestBody GetAccountReq getAccountReq) {
       AccountDto accountDto =accountService.getAccount(getAccountReq);
       return ResponseEntity.ok(new ReqAndRes.ResData<>("success", accountDto));
   }


}
