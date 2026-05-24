package com.myproject.ecommerce_service.dto.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointBalanceResponse {
   private Long userId;
   private int currentBalance;
}
