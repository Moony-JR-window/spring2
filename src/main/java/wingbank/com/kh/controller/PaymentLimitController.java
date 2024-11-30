package wingbank.com.kh.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wingbank.com.kh.dto.PaymentLimitDto;
import wingbank.com.kh.model.PaymentLimit;
import wingbank.com.kh.service.PaymentLimitService;

@RestController
@RequestMapping("/v1/paymentlimit")
@Tag(name = "Payment Limit")
public class PaymentLimitController {

    final PaymentLimitService paymentLimitService;

    public PaymentLimitController(PaymentLimitService paymentLimitService) {
        this.paymentLimitService = paymentLimitService;
    }

    @PostMapping("/create")
    public PaymentLimit createPaymentLimit(@RequestBody PaymentLimitDto paymentLimitDto) {
        PaymentLimit paymentLimit = paymentLimitService.createPaymentLimit(paymentLimitDto);
        return paymentLimit;
    }
}
