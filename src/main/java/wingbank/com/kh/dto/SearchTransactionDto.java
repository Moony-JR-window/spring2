package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.model.SearchTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SearchTransactionDto {
    private BigDecimal transactionAmount;
    private LocalDate transactionDate;
    private Integer transactionId;
    private Integer accountId;
    private Integer transactionTypeId;


    public static SearchTransactionDto mapToDto(SearchTransaction searchTransaction) {
        wingbank.com.kh.dto.SearchTransactionDto dto = new wingbank.com.kh.dto.SearchTransactionDto();
        dto.setTransactionAmount(searchTransaction.getTransactionAmount());
        dto.setTransactionDate(searchTransaction.getTransactionDate());
        dto.setTransactionId(searchTransaction.getTransaction().getTransactionId());
        dto.setAccountId(searchTransaction.getAccount().getAccountId());
        dto.setTransactionTypeId(searchTransaction.getTransactionType().getTransactionTypeId());
        return dto;

    }

}
