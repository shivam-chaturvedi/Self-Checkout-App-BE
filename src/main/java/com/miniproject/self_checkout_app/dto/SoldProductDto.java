package com.miniproject.self_checkout_app.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SoldProductDto {
	private List<Map<LocalDateTime,Long>> timeStampsAndQuantitySold=new ArrayList<Map<LocalDateTime,Long>>();
	
	private Long totalQuantitySold=0L;
	
	private Long quantityLeftInStock=0L;
	private Double totalSoldAmount=0D;
    private String productName;
    private Double productPrice=0D;
    private String category;
    
}
