package wingbank.com.kh.unit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wingbank.com.kh.controller.RecentTransactionController;
import wingbank.com.kh.dto.RecentTransactionDto;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.model.RecentTransaction;
import wingbank.com.kh.service.RecentTransactionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecentTransactionTest {

    @InjectMocks
    private RecentTransactionController recentTransactionController;

    @Mock
    private RecentTransactionService recentTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRecent() {
        RecentTransactionDto recentTransactionDto = new RecentTransactionDto();
        RecentTransaction mockRecentTransaction = new RecentTransaction();

        when(recentTransactionService.createRecentTransaction(any(RecentTransactionDto.class)))
                .thenReturn(mockRecentTransaction);

        ResponseEntity<ReqAndRes.ResData<RecentTransaction>> response =
                recentTransactionController.createRecent(recentTransactionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getMessage());
        assertEquals(mockRecentTransaction, response.getBody().getData());
    }

    @Test
    void testDeleteRecent() {
        int transactionId = 1;
        when(recentTransactionService.DeleteRecentTransaction(transactionId))
                .thenReturn("Deleted");

        ResponseEntity<String> response = recentTransactionController.deleteRecent(transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Recent transaction for accountId 1 deleted successfully", response.getBody());
    }

}
