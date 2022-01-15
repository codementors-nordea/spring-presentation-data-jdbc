package pl.codementors.jdbc.purchase;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PurchasePrice {
    BigDecimal price;
}
