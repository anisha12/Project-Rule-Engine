package com.anisha.promoEngine;

import com.anisha.promoEngine.Model.Cart;
import com.anisha.promoEngine.Model.Product;
import com.anisha.promoEngine.promoStratgey.IPromotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromotionEngine {

    public Double getRawPrice(Cart cart){
        Double firstPrice = 0.0;
        for(Map.Entry<Product, Integer> entry : cart.getContent().entrySet()){
            firstPrice = firstPrice + entry.getValue() * PriceList.getPrice(entry.getKey().getSku());
        }
        return firstPrice;
    }

    public Double getPromotedPrice(Cart cart , List<IPromotion> promotions){
        Double rawPrice = getRawPrice(cart);
        return applyPromotion(cart, promotions, rawPrice);
    }

    private Double applyPromotion(Cart cart, List<IPromotion> promotions, Double checkoutPrice){
        IPromotion selectedPromotion = null;
         double discountedPrice = 0.0;

         List<IPromotion> availablePromotion = new ArrayList<>();
         for(IPromotion promotion : promotions){
            if (promotion.isAvailable(cart)){
                availablePromotion.add(promotion);
             }

         }
        // Find the promotion to apply on cart and the discounted price with it over the cart.
        for (IPromotion promotion: availablePromotion) {
            double promotionDiscountedPrice = promotion.getDiscountedPrice();
            if (promotionDiscountedPrice > discountedPrice) {
                discountedPrice = promotionDiscountedPrice;
                selectedPromotion = promotion;
            }
        }

        // Adjust the items in the cart after promotions are applied
        Cart promotedCart = new Cart();
        if (selectedPromotion != null) {
            promotedCart = selectedPromotion.applyPromotion(cart);
        }
        // Subtract the discounted price from the cart price since promotion was applied.
        checkoutPrice = checkoutPrice - discountedPrice;
        // Recursive call to apply all available promotions on cart
        // Cart contents should be properly reduced so that the recursion loop will not occur.
        return applyPromotion(promotedCart, availablePromotion, checkoutPrice);
    }

    }
}
