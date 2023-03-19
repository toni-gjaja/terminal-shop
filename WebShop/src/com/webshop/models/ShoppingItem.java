package com.webshop.models;

import com.webshop.IProduct;
import com.webshop.IShoppingItem;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class ShoppingItem implements IShoppingItem {

    Long id;

    IProduct product;

    BigDecimal quantity;

    Date modifyDate;

    public ShoppingItem(IProduct product, BigDecimal quantity) {
        Random rnd = new Random();
        this.id = rnd.nextLong();
        if(this.id < 0){this.id = this.id * -1;}
        this.product = product;
        this.quantity = quantity;

    }

    @Override
    public void setModifyDate() {

        this.modifyDate = Date.from(getInstant());
    }

    @Override
    public boolean addQuantity(BigDecimal quantity){

        this.quantity = this.quantity.add(quantity);
        return true;

    }

    @Override
    public boolean removeQuantity(BigDecimal quantity){

        this.quantity = this.quantity.subtract(quantity);
        return true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public IProduct getProduct() {
        return product;
    }

    @Override
    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public BigDecimal getPrice() {
        return product.getPrice();
    }

    @Override
    public Date getLastModifyTime() {
        return modifyDate;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return product.getPrice().multiply(quantity);
    }

    public Instant getInstant(){

        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();

    }

    @Override
    public String toString() {
        return product.getName() + " " + quantity;
    }
}
