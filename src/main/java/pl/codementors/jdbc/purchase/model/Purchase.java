package pl.codementors.jdbc.purchase.model;


import lombok.EqualsAndHashCode;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.math.BigDecimal;
import java.util.*;

public class Purchase {
    private static final Integer ITEMS_REQUIRED_FOR_DISCOUNT = 5;

    @Id
    private final Long id;
    private final PurchaseStatus status;
    @With
    private final Set<Item> items;

    @PersistenceConstructor
    Purchase(Long id, PurchaseStatus status, Set<Item> items) {
        this.id = id;
        this.status = status;
        this.items = items;
    }

    public static Purchase start() {
        return new Purchase(null, PurchaseStatus.IN_PROGRESS, Set.of());
    }

    public Purchase addItem(Item item) {
        if (!isInProgress()) {
            throw new IllegalStateException("Purchase needs to be in in progress state");
        }
        if (shouldApplyDiscount()) {
            var discountedItem = item.applyDiscount(BigDecimal.TEN);
            return withItem(discountedItem);
        }

        return withItem(item);
    }

    public Purchase finish() {
        return new Purchase(id, PurchaseStatus.FINISHED, items);
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isInProgress() {
        return status == PurchaseStatus.IN_PROGRESS;
    }

    private boolean shouldApplyDiscount() {
        return items.size() > ITEMS_REQUIRED_FOR_DISCOUNT;
    }

    private Purchase withItem(Item item) {
        var newItems = new HashSet<>(items);
        newItems.add(item);

        return new Purchase(id, status, newItems);
    }
}
