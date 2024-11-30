package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.UnlockTypeDto;
import wingbank.com.kh.model.Account;
import wingbank.com.kh.model.UnlockType;
import wingbank.com.kh.repository.UnlockTypeRepository;
import wingbank.com.kh.service.UnlockService;

@Slf4j
@Service
public class UnlockServiceImp implements UnlockService {
    final UnlockTypeRepository unlockTypeRepository;

    public UnlockServiceImp(UnlockTypeRepository unlockTypeRepository) {
        this.unlockTypeRepository = unlockTypeRepository;
    }

    @Override
    public UnlockType unlockType(UnlockTypeDto unlockTypeDto, Account account) {
       UnlockType unlockType= UnlockTypeDto.toEntity(unlockTypeDto, account );
       log.info("==================== {} ",account.getAccountId());
       log.info("============ {} " , unlockTypeDto.getAccountId());
       log.info("unlockType: {}", unlockType);
        unlockTypeRepository.save(unlockType);
        return unlockType;
    }
}
