package wingbank.com.kh.unit_test;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import wingbank.com.kh.controller.SearchTransactionController;
import wingbank.com.kh.service.SearchTransactionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchTransactionTest {

    @InjectMocks
    private SearchTransactionController searchTransactionController;

    @Mock
    private SearchTransactionService searchTransactionService;

    public SearchTransactionTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCloneTransaction() {
        // Mock service behavior
        doNothing().when(searchTransactionService).cloneSearchTransactions();

        // Call the controller method
        ResponseEntity<String> response = searchTransactionController.cloneTransaction();

        // Assertions
        assertEquals("Search transactions populated successfully!", response.getBody());
        verify(searchTransactionService, times(1)).cloneSearchTransactions();
    }

}
