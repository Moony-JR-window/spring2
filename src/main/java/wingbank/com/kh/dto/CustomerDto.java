package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import wingbank.com.kh.enums.AccountStatus;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.Customer;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerDto {
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private AccountStatus accountStatus;
//    private List<AccountDto> listAccounts;


    public static CustomerDto DtoMapper(Customer customer) {;
        CustomerDto dto = new CustomerDto();
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhoneNumber());
        dto.setFullName(customer.getFullName());
        dto.setAccountStatus(customer.getAccountStatus());
        dto.setBirthDate(customer.getDateOfBirth());
        return dto;
    }

    public static Customer CustomerMapper(CustomerDto customerDto) {

        if (!isValidEmail(customerDto.getEmail())) {
            throw new CustomException("Invalid email address. Email must end with '@email.com'", HttpStatus.BAD_REQUEST);
        }
        Customer customer = new Customer();
        customer.setEmail(customerDto.getEmail());
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhone());
        customer.setDateOfBirth(customerDto.getBirthDate());
        customer.setCreatedAt(LocalDate.now());

        if (customerDto.getAccountStatus() == null) {
            customer.setAccountStatus(AccountStatus.active);
        } else {
            customer.setAccountStatus(customerDto.getAccountStatus());
        }
        return customer;
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Ensure email ends with '@email.com'
        return email.endsWith("@gmail.com");
    }
}
