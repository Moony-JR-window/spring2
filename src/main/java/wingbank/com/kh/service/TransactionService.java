package wingbank.com.kh.service;

import wingbank.com.kh.dto.TransactionDto;
import wingbank.com.kh.model.Transaction;

import java.security.NoSuchAlgorithmException;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto) ;
    String CheckPassword(Integer AccountNumber,String password) ;
}
