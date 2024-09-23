package com.example.sneakers.features.sneaker.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SneakerSupplierDTO {
  private Long sneakerId;
  private String sneakerName;
  private String sneakerBrand;
  private BigDecimal sneakerPrice;
  private String sneakerDescription;
  private String sneakerImageUrl;
  private Long supplierId;
  private String supplierUsername;
  private String supplierEmail;
}