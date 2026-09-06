package com.codingshuttle.razorpay.payment.processor.strategy;

import com.codingshuttle.razorpay.common.util.RandomizerUtil;
import com.codingshuttle.razorpay.payment.processor.PaymentProcessor;
import com.codingshuttle.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.codingshuttle.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest paymentProcessorRequest) {
         final String VPA_CODE_FAIL="fail@okaxis";

         String bankCode=paymentProcessorRequest.methodDetails()!=null ?
                 paymentProcessorRequest.methodDetails().get("vpa").toString():null;

         if (VPA_CODE_FAIL.equals(bankCode)){
            return new PaymentProcessorResponse.Failure("UPI_REJECTED",
                    "Banked rejected the transaction registration");
         }

         String processorRef="UPI_PROCESSOR_"+ RandomizerUtil.randomBase64(16);

         String bankRef="BANK_REF"+RandomizerUtil.randomBase64(16);

        return new PaymentProcessorResponse.Success(processorRef,bankRef);
    }
}
