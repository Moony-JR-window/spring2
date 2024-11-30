package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.CustomerDto;
import wingbank.com.kh.model.Customer;
import wingbank.com.kh.repository.AccountRepository;
import wingbank.com.kh.repository.CustomerRepository;
import wingbank.com.kh.service.CustomerService;

@Slf4j
@Service
public class CustomerServiceImp implements CustomerService {

    final CustomerRepository customerRepository;
    final AccountRepository accountRepository;

    public CustomerServiceImp(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerDto.CustomerMapper(customerDto);
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDto getCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Error"));
        return CustomerDto.DtoMapper(customer);
    }


}
