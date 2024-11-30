package wingbank.com.kh.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.ReqAndRes;
import wingbank.com.kh.service.serviceImp.MessageCacheImp;

@Slf4j
@RestController
@RequestMapping("cached")
public class CacheController {
    final MessageCacheImp messageCacheImp;

    public CacheController(MessageCacheImp messageCacheImp) {
        this.messageCacheImp = messageCacheImp;
    }

    @PostMapping("/refresh")
    @PostConstruct
    public ResponseEntity<ReqAndRes.ResData<String>> refresh() {
        log.info("Cached ========================== {} ","Reloading======" );
        MessageCacheImp.loadingCache();
        log.info("Cached ========================== {} ","Success=========" );
        return ResponseEntity.ok(new ReqAndRes.ResData<>("Successfully Reload","Good"));
    }
}
