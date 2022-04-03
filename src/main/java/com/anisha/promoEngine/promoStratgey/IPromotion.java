package com.anisha.promoEngine.promoStratgey;

import com.anisha.promoEngine.Model.Cart;


public interface IPromotion {

    Boolean isAvailable(Cart cart);

    Double getDiscountedPrice();

    Cart applyPromotion(Cart cart);

}
