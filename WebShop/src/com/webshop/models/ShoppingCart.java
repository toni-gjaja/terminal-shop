package com.webshop.models;

import com.webshop.IProduct;
import com.webshop.IShoppingCart;
import com.webshop.IShoppingItem;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ShoppingCart implements IShoppingCart {

    Long id;

    String user;

    Date createdTime;

    List<IShoppingItem> items;

    public ShoppingCart(String user) {

        Random rnd = new Random();
        this.id = rnd.nextLong();
        if(this.id < 0){this.id = this.id * -1;}
        this.user = user;
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        this.createdTime = Date.from(instant);
        this.items = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public List<IShoppingItem> getItems() {
        return items;
    }

    @Override
    public BigDecimal getTotalPrice() {

        BigDecimal total = BigDecimal.ZERO;

        for (IShoppingItem si : items){

             total = total.add(si.getTotalAmount());

        }

        return total;

    }

    @Override
    public boolean addItem(IProduct product, BigDecimal quantity) {

        for(IShoppingItem si : items){

            if (Objects.equals(si.getProduct().getName(), product.getName())){

                si.addQuantity(quantity);

                return true;

            }

        }

        return items.add(new ShoppingItem(product, quantity));

    }

    @Override
    public boolean removeItem(IProduct product, BigDecimal quantity) {

        IShoppingItem si = items.stream()
                .filter(iShoppingItem -> iShoppingItem.getProduct().getName().equals(product.getName()))
                .findFirst()
                .orElse(null);

        if (si == null){return false;}

        return (si.getQuantity().compareTo(quantity) < 1 ? items.remove(si) : si.removeQuantity(quantity));

    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean checkOut() {
        return false;
    }
}
