package wingbank.com.kh.service;


import wingbank.com.kh.dto.FilterDto;
import wingbank.com.kh.dto.SearchTransactionDto;
import wingbank.com.kh.dto.SearchTransactionReq;

import java.util.List;

public interface SearchTransactionService {

    List<SearchTransactionDto> getSearchTransactions( FilterDto filterDto);
    List<SearchTransactionDto> getSearchTransactionsType(SearchTransactionReq searchTransactionReq);
    void cloneSearchTransactions();

}
