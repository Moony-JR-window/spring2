package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.model.TransactionType;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionTypeDto {
    private String transactionName;
    private String description;
    private LocalDate createAt;

    public static TransactionType TrasactionTypeMapper(TransactionTypeDto transactionTypeDto) {
        TransactionType transactionType = new TransactionType();
        transactionType.setCreateAt(LocalDate.now());
        transactionType.setDescription(transactionTypeDto.getDescription());
        transactionType.setTransactionName(transactionTypeDto.getTransactionName());
        return transactionType;
    }
}
