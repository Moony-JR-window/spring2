package wingbank.com.kh.service.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wingbank.com.kh.dto.FilterDto;
import wingbank.com.kh.dto.SearchTransactionDto;
import wingbank.com.kh.exception.CustomException;
import wingbank.com.kh.model.SearchTransaction;
import wingbank.com.kh.repository.SearchTransactionRepository;
import wingbank.com.kh.dto.SearchTransactionReq;
import wingbank.com.kh.service.SearchTransactionService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchTransactionServiceImp implements SearchTransactionService {

    final SearchTransactionRepository searchTransactionRepository;

    public SearchTransactionServiceImp(SearchTransactionRepository searchTransactionRepository) {
        this.searchTransactionRepository = searchTransactionRepository;
    }

    //Mapper Data Feature
    private SearchTransactionDto mapToDto(SearchTransaction searchTransaction) {
        SearchTransactionDto dto = new SearchTransactionDto();
        dto.setTransactionAmount(searchTransaction.getTransactionAmount());
        dto.setTransactionDate(searchTransaction.getTransactionDate());
        dto.setTransactionId(searchTransaction.getTransaction().getTransactionId());
        dto.setAccountId(searchTransaction.getAccount().getAccountId());
        dto.setTransactionTypeId(searchTransaction.getTransactionType().getTransactionTypeId());
        return dto;
    }

    @Override
    public List<SearchTransactionDto> getSearchTransactions(FilterDto filterDto) {

        List<SearchTransaction> searchTransactions = searchTransactionRepository.findRecentTransactionsByFilterDto(filterDto);

        if (searchTransactions.isEmpty()) {
            throw new CustomException("Not Found", HttpStatus.NOT_FOUND);
        }

        return searchTransactions.stream().map(this::mapToDto).collect(Collectors.toList());
        //This is call this::mapToDto

    }


    @Override
    public List<SearchTransactionDto> getSearchTransactionsType(SearchTransactionReq searchTransactionReq) {

        Integer account_id = searchTransactionReq.getAccountId();
        String transaction_name = searchTransactionReq.getTransactionName();
        List<SearchTransaction> searchTransactions = searchTransactionRepository.findByAccountIdAndTransactionTypeName(account_id, transaction_name);

        if (searchTransactions.isEmpty()) {
            throw new CustomException("Data Not Found account_id " + searchTransactionReq.getAccountId() + " at " + searchTransactionReq.getTransactionName(), HttpStatus.NOT_FOUND);
        }
        return searchTransactions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void cloneSearchTransactions() {
        searchTransactionRepository.populateSearchTransactions();
    }

}
