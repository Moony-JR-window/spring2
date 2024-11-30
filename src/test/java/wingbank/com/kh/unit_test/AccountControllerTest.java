package wingbank.com.kh.unit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import wingbank.com.kh.controller.AccountController;
import wingbank.com.kh.dto.AccountDto;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.enums.AccountStatus;
import wingbank.com.kh.enums.AccountType;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.service.AccountService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() {
        // Arrange: set up mock data and behavior
        AccountDto accountDto = new AccountDto();

        Account account = new Account();
        account.setAccountId(1);
        account.setAccountNumber(1234);
        account.setAccountStatus(AccountStatus.active);
        account.setCurrency("KHR");
        account.setAccountType(AccountType.checking);
        account.setBalance(new BigDecimal("0.03"));

        when(accountService.createAccount(any(AccountDto.class))).thenReturn(account);

        // Act: call the method under test
        ResponseEntity<ReqAndRes.ResData<Account>> response = accController.createAccount(accountDto);

        // Assert: verify the results
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody().getMessage());
        assertEquals(account, response.getBody().getData());
    }
}
