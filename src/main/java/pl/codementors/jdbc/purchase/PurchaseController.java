package pl.codementors.jdbc.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.codementors.jdbc.purchase.model.Item;
import pl.codementors.jdbc.purchase.model.Purchase;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/purchase/{id}/price")
    public PurchasePrice getPrice(@PathVariable Long id) {
        return purchaseService.getTotalPrice(id);
    }

    @PostMapping("/purchase")
    public Purchase start() {
        return purchaseService.startPurchase();
    }

    @PutMapping("/purchase/{id}/items")
    public void addItem(@PathVariable Long id, @RequestBody Item item) {
        purchaseService.addItem(id, item);
    }

    @PutMapping("/purchase/{id}/finish")
    public void finish(@PathVariable Long id) {
        purchaseService.finish(id);
    }
}
