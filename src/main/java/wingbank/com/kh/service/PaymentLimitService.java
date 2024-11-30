package wingbank.com.kh.service;

import wingbank.com.kh.dto.PaymentLimitDto;
import wingbank.com.kh.model.PaymentLimit;

public interface PaymentLimitService {
    PaymentLimit createPaymentLimit(PaymentLimitDto paymentLimitDto);
}
