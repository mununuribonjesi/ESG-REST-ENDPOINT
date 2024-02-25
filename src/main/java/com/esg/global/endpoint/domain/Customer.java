package com.esg.global.endpoint.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table("customer")
public record Customer(@Id Long customerRef, String customerName, String addressLine1, String addressLine2, String town,
                       String county, String country, String postcode) {

}