package pl.codementors.jdbc.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.codementors.jdbc.purchase.model.Item;
import pl.codementors.jdbc.purchase.model.Purchase;
import pl.codementors.jdbc.purchase.model.PurchaseRepository;

@Component
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public Purchase startPurchase() {
        return purchaseRepository.save(Purchase.start());
    }

    public void addItem(Long purchaseId, Item item) {
        var purchase = getPurchaseById(purchaseId);
        var updatedPurchase = purchase.addItem(item);

        purchaseRepository.save(updatedPurchase);
    }

    public void finish(Long purchaseId) {
        var purchase = getPurchaseById(purchaseId);
        var finishedPurchase = purchase.finish();

        purchaseRepository.save(finishedPurchase);
    }

    public PurchasePrice getTotalPrice(Long purchaseId) {
        var purchase = getPurchaseById(purchaseId);
        return new PurchasePrice(purchase.getTotalPrice());
    }

    private Purchase getPurchaseById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
