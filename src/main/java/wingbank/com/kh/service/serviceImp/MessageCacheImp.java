package wingbank.com.kh.service.serviceImp;

import org.springframework.stereotype.Service;
import wingbank.com.kh.model.MessageResponse;
import wingbank.com.kh.repository.MessageRespository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service

public class MessageCacheImp {

    static MessageRespository messageRepository;

   static Map<String, Object> errorDetails = new LinkedHashMap<>();

    public MessageCacheImp(MessageRespository messageRepository) {
        MessageCacheImp.messageRepository = messageRepository;
    }

    public static void  loadingCache() {
        // Querying data from the repository
        List<MessageResponse> messages = messageRepository.findAll();

        // Looping through the messages and storing them in the cache
        for (MessageResponse message : messages) {
            errorDetails.put(message.getCode(), message);  // Assuming 'code' is the unique key
        }
    }

    public static MessageResponse getDataFromCache(String code) {
            return (MessageResponse) errorDetails.get(code);
        }


//    @Bean
//    public Map<String, Map<String, String>> messageCache() {
//        List<MessageResponse> messages = messageRepository.findAll();
//
//        Map<String, Map<String, String>> messageCache = new HashMap<>();
//        Map<String, String> defaultMessages = new HashMap<>();
//        Map<String, String> khmerMessages = new HashMap<>();
//        Map<String, String> englishMessages = new HashMap<>();
//
//        for (MessageResponse message : messages) {
//            defaultMessages.put(message.getCode(), message.getMessage());
//            khmerMessages.put(message.getCode(), message.getMessageKh());
//            englishMessages.put(message.getCode(), message.getMessageEn());
//        }
//
//        messageCache.put("default", defaultMessages);
//        messageCache.put("kh", khmerMessages);
//        messageCache.put("en", englishMessages);
//
//        return messageCache;
//    }
}
