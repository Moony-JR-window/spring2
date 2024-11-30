package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAccountReq {
    private Integer accountId;
    private String password;
}
