package wingbank.com.kh;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wingbank.com.kh.service.serviceImp.MessageCacheImp;
@Slf4j
@SpringBootApplication
public class WingbankApplication {
	final MessageCacheImp messageCacheImp;

    public WingbankApplication(MessageCacheImp messageCacheImp) {
        this.messageCacheImp = messageCacheImp;
    }

    public static void main(String[] args) {
		SpringApplication.run(WingbankApplication.class, args);
	}

	@PostConstruct
	void pushCached(){
		log.info("pushCached ============================= ");
		MessageCacheImp.loadingCache();
		log.info("End ============================ pushCached");
	}

}
