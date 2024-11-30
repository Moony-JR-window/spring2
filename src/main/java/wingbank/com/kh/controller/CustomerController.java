package wingbank.com.kh.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wingbank.com.kh.dto.CustomerDto;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.model.Customer;
import wingbank.com.kh.service.CustomerService;

@Tag(name = "Customer")
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReqAndRes.ResData<Customer>> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.createCustomer(customerDto);
            return ResponseEntity.ok(new ReqAndRes.ResData<>("success", customer));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReqAndRes.ResData<CustomerDto>> getCustomer(@PathVariable("id") Integer customerId) {
        CustomerDto customerDto = customerService.getCustomer(customerId);
        return ResponseEntity.ok(new ReqAndRes.ResData<>("success", customerDto));
    }
}
