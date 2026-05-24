package com.myproject.ecommerce_service.dto.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointChargeRequest {
    private Long userId;
    private int chargeAmount;
}
