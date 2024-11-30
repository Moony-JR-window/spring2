package wingbank.com.kh.service.serviceImp;

import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.TransactionTypeDto;
import wingbank.com.kh.model.TransactionType;
import wingbank.com.kh.repository.TransactionTypeRepository;
import wingbank.com.kh.service.TransactionTypeSevice;

@Service
public class TransactionTypeImp implements TransactionTypeSevice {

    final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeImp(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public TransactionType createTransactionType(TransactionTypeDto transactionTypeDto) {

        TransactionType transactionType = TransactionTypeDto.TrasactionTypeMapper(transactionTypeDto);

        return transactionTypeRepository.save(transactionType);
    }
}
