package wingbank.com.kh.service;

import wingbank.com.kh.dto.TransactionTypeDto;
import wingbank.com.kh.model.TransactionType;

public interface TransactionTypeSevice {

    TransactionType createTransactionType(TransactionTypeDto transactionTypeDto);
}
