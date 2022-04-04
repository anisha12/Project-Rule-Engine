package com.anisha.promoEngine.promoStratgey;

import com.anisha.promoEngine.model.Cart;
import com.anisha.promoEngine.model.Product;
import com.anisha.promoEngine.PriceList;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class MultipleSKUIPromo implements IPromotion {

    private List<String> appliedItems = new ArrayList<>();
    private Double promotedPrice;
    private Map<String, Boolean> availabilityCheckMap = new HashMap<>();

    @Override
    public Boolean isAvailable(Cart cart) {
        appliedItems.forEach(i-> availabilityCheckMap.put(i,false));

        for(Map.Entry<Product, Integer> entry: cart.getContent().entrySet()){
            if(appliedItems.contains(entry.getKey().getSku())){
                availabilityCheckMap.put(entry.getKey().getSku(), true);
            }
            return !availabilityCheckMap.containsValue(false);
        }
        return null;
    }

    @Override
    public Double getDiscountedPrice() {
        double itemPrice = 0.0;
        for(String sku: appliedItems)
            itemPrice += PriceList.getPrice(sku);

        return itemPrice - this.promotedPrice;
    }

    @Override
    public Cart applyPromotion(Cart cart) {
        return null;
    }
}
