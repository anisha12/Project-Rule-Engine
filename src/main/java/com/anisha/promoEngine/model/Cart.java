package com.anisha.promoEngine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Cart {
     private Map<Product, Integer> content;
      public Cart(){

      }
       public Cart(Map<Product,Integer> content){
          this.content = content;

       }

      public Integer getQuantity(String sku){
          for(Map.Entry<Product, Integer> entry: this.content.entrySet()){
             if( entry.getKey().getSku().equalsIgnoreCase(sku)){
                 return entry.getValue();
             }
          }
          return 0;
      }

      public Map<Product, Integer> removeItem(String itemToRemove){
          Product productToRemove = null;
          Map<Product, Integer> temp = new HashMap<>();
          temp.putAll(this.content);

          for (Map.Entry<Product, Integer> kv: temp.entrySet()) {
              if (kv.getKey().getSku().equalsIgnoreCase(itemToRemove)) {
                  productToRemove = kv.getKey();
              }
          }
          if (itemToRemove != null) {
              temp.remove(productToRemove);
          }
          return temp;

      }
    public Map<Product, Integer> adjustInventory(String itemName, Integer quantity) {
        Product productAdj = null;
        Map<Product, Integer> temp = new HashMap<>();
        temp.putAll(this.content);

        for (Map.Entry<Product, Integer> kv: temp.entrySet()) {
            if (kv.getKey().getSku().equalsIgnoreCase(itemName)) {
                productAdj = kv.getKey();
            }
        }
        if (null != productAdj) {
            temp.put(productAdj, quantity);
        }
        return temp;
    }


}
