package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTransactionReq {
    private Integer accountId;
    private String transactionName;

}
