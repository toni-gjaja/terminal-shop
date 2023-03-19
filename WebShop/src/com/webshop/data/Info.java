package com.webshop.data;

public class Info {

    public static void printInfo(){

        System.out.println("Commands for our shop are as following:");
        System.out.println("ADD-ITEM Pineapple 20 - after command you type name of the fruit followed by desired amount, if amount is higher than stock amount, you will get full stock amount");
        System.out.println("REMOVE-ITEM Pineapple 20 - after command you type name of the fruit you want to remove from cart and its quantity, if quantity matches total quantity," +
                " product will be removed from cart, otherwise quantity will be lowered for typed quantity");
        System.out.println("CHECK-AMOUNT Pineapple - after command you type name of the product you have in cart to check total price amount for the product");
        System.out.println("GET-PRODUCT-INFO Pineapple - after command you type name of the product you want information for");
        System.out.println("SHOW-PRODUCTS - command that returns all products in shop");
        System.out.println("GET-CART-ID - command that returns cart id");
        System.out.println("GET-CUSTOMER-NAME - command that returns current cart owner");
        System.out.println("EMPTY-CART - command that removes all items from cart");
        System.out.println("CHECK-CART - command that returns all products in your cart with quantity of each one");
        System.out.println("CHECK-CART-PRICE - command that returns total price amount for your cart");
        System.out.println("CHECK-OUT - command that shows you all the products you bought and check you out of the shop");
        System.out.println("EXIT - exit shop\n");

    }

}
