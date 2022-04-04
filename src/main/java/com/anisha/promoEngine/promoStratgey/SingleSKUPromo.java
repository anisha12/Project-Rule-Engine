package com.anisha.promoEngine.promoStratgey;

import com.anisha.promoEngine.model.Cart;
import com.anisha.promoEngine.model.Product;
import com.anisha.promoEngine.PriceList;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SingleSKUPromo implements IPromotion {
    private String appliedItem;

    private Integer quota;

    private Double promotedPrice;


    @Override
    public Boolean isAvailable(Cart cart) {
        for(Map.Entry<Product, Integer> entry : cart.getContent().entrySet()){
            if(entry.getKey().getSku().equalsIgnoreCase(appliedItem) && entry.getValue() >=quota){
                return true;
            }
        }
        return false;
    }

    @Override
    public Double getDiscountedPrice() {
        return (PriceList.getPrice(appliedItem)*this.quota)- this.promotedPrice;
    }

    @Override
    public Cart applyPromotion(Cart cart) {
        if(cart.getContent().isEmpty()){
            return cart;
        }
        if(!isAvailable(cart)){
            System.out.println("No Promo Applicable");
        }

        Cart promotedCart = new Cart(cart.getContent());
        int cartQuantity = promotedCart.getQuantity(appliedItem);
        Map<Product,Integer> updatedContent = new HashMap<>();

        if(cartQuantity-quota==0){
            updatedContent.putAll(promotedCart.removeItem(appliedItem));
        }else {
            updatedContent.putAll(promotedCart.adjustInventory(appliedItem, cartQuantity - quota));
        }

        promotedCart.setContent(updatedContent);
        return promotedCart;
    }
}
