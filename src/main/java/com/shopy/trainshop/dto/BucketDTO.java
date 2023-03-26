package com.shopy.trainshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
    private Integer amountProducts;
    private BigDecimal totalPrice;
    private List<BucketItemDTO> bucketItem = new ArrayList<>();
//    public void aggregate() {
//        this.amountProducts = bucketItem.size();
//        this.totalPrice = BigDecimal.valueOf(bucketItem.stream()
//                .map(BucketItemDTO::getSum)
//                .mapToDouble(Double::doubleValue)
//                .sum());
//    }
}
