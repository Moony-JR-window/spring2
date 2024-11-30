package wingbank.com.kh.dto;

import lombok.Getter;
import lombok.Setter;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.UnlockType;

@Getter
@Setter
public class UnlockTypeDto {
    private String unlockTypeId;
    private String unlockTypeName;
    private Integer AccountId;

    public static UnlockType toEntity(UnlockTypeDto unlockTypeDto, Account account) {
        UnlockType unlockType = new UnlockType();
        unlockType.setUnlockTypeName(unlockTypeDto.getUnlockTypeName());
        unlockType.setAccount(account);
        return unlockType;
    }
}
