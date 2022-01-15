package pl.codementors.jdbc.purchase.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

    private final String name;
    private final BigDecimal price;
    private final BigDecimal discountedPrice;

    @PersistenceConstructor
    Item(String name, BigDecimal price, BigDecimal discountedPrice) {
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    @JsonCreator
    public Item(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.discountedPrice = BigDecimal.ZERO;
    }

    public Item applyDiscount(BigDecimal discount) {
        return new Item(name, price, price.multiply(discount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    }

    public BigDecimal getPrice() {
        return price.subtract(discountedPrice);
    }
}
