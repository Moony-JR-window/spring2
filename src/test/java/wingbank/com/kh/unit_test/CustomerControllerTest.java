package wingbank.com.kh.unit_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import wingbank.com.kh.controller.CustomerController;
import wingbank.com.kh.dto.CustomerDto;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.model.Customer;
import wingbank.com.kh.service.CustomerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName("John Doe");
        customerDto.setEmail("john.doe@example.com");

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFullName("John Doe");
        customer.setEmail("john.doe@example.com");

        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(customer);

        // Act
        ResponseEntity<ReqAndRes.ResData<Customer>> response = customerController.createCustomer(customerDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("success", response.getBody().getMessage());
        assertEquals(customer, response.getBody().getData());
    }

    @Test
    void testGetCustomer() {
        // Arrange
        int customerId = 1;

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFullName("John Doe");
        customerDto.setEmail("john.doe@example.com");

        when(customerService.getCustomer(customerId)).thenReturn(customerDto);

        // Act
        ResponseEntity<ReqAndRes.ResData<CustomerDto>> response = customerController.getCustomer(customerId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("success", response.getBody().getMessage());
        assertEquals(customerDto, response.getBody().getData());
    }
}
