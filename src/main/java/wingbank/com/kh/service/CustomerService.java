package wingbank.com.kh.service;

import wingbank.com.kh.dto.CustomerDto;
import wingbank.com.kh.model.Customer;

public interface CustomerService {

    Customer createCustomer(CustomerDto customerDto);

    CustomerDto getCustomer(Integer customerId);
}
