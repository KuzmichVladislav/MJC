package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.exception.ExceptionKey.USER_ID_IS_NOT_VALID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto extends RepresentationModel<OrderDto> {

    private long id;
    @NotNull(message = ExceptionKey.USER_ID_MIGHT_NOT_BE_NULL)
    @Min(value = 0, message = USER_ID_IS_NOT_VALID)
    private long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime purchaseTime;
    private List<OrderCertificateDetailsDto> orderCertificateDetailsDtos;
    private BigDecimal totalCost;
}
