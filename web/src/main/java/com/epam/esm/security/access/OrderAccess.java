package com.epam.esm.security.access;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.security.entity.JwtUserDetails;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAccess {

    private final OrderService orderService;

    public boolean canDeleteOrder(Authentication authentication, long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        return orderDto.getUserId() == userDetails.getUserId();
    }
}
