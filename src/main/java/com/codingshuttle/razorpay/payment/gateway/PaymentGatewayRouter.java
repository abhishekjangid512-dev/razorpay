package com.codingshuttle.razorpay.payment.gateway;

import com.codingshuttle.razorpay.common.enums.PaymentMethod;
import com.codingshuttle.razorpay.payment.gateway.dto.PaymentRequest;
import com.codingshuttle.razorpay.payment.gateway.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {

    private final Map<PaymentMethod,PaymentAdapter> paymentAdapters;

    public PaymentResult initiate(PaymentRequest paymentRequest){

        PaymentAdapter adapter=paymentAdapters.get(paymentRequest.method());

        if (adapter == null){
            throw new IllegalArgumentException(
                    "No payment adapter registered for method: "
                    +paymentRequest.method()
            );
        }

        return adapter.initiate(paymentRequest);


    }
    public PaymentResult capture(PaymentMethod paymentMethod, UUID paymentId){
        PaymentAdapter adapter= paymentAdapters.get(paymentMethod);

        if (adapter == null) {
            throw new IllegalArgumentException(
                    "No payment adapter registered for method: " + paymentMethod
            );
        }

        return adapter.capture(paymentId);
    }
}
