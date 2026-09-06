package com.codingshuttle.razorpay.payment.statemachine;

import com.codingshuttle.razorpay.common.enums.PaymentActor;
import com.codingshuttle.razorpay.common.enums.PaymentEvent;
import com.codingshuttle.razorpay.common.enums.PaymentStatus;
import com.codingshuttle.razorpay.payment.entity.Payment;
import com.codingshuttle.razorpay.payment.entity.PaymentTransitionLog;
import com.codingshuttle.razorpay.payment.repository.PaymentTransitionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentTransitionService {

    private final PaymentTransitionLogRepository paymentTransitionLogRepository;

    private final PaymentStateMachine paymentStateMachine;

    public PaymentStatus apply(Payment payment, PaymentEvent paymentEvent) {
        PaymentStatus oldStatus = payment.getStatus();
        PaymentStatus nextStatus = paymentStateMachine.transition(oldStatus, paymentEvent);
        payment.setStatus(nextStatus);
        PaymentTransitionLog paymentTransitionLog = PaymentTransitionLog.builder()
                .payment(payment)
                .fromStatus(oldStatus)
                .event(paymentEvent)
                .toStatus(nextStatus)
                .actor(PaymentActor.SYSTEM)
                .occurredAt(LocalDateTime.now())
                .build();
        paymentTransitionLogRepository.save(paymentTransitionLog);
        return nextStatus;
    }
}
