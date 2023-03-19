import com.webshop.IProduct;
import com.webshop.IShoppingCart;
import com.webshop.IShoppingItem;
import com.webshop.data.DataProvider;
import com.webshop.data.Info;
import com.webshop.models.ShoppingCart;
import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.util.*;

public class Main {

    static List<IProduct> warehouse = DataProvider.getData();

    public static void main(String[] args) {

        System.out.println("\n\t\t\t\t\t\t\tWelcome to fruit shop!!!\n");

        Info.printInfo();

        boolean confirmation = true;

        do {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter shop (YES/NO): ");
            String answer = sc.nextLine();
            switch (answer) {
                case "YES" -> shopping();
                case "NO" -> confirmation = false;
                default -> System.out.println("Incorrect input");
            }

        }while (confirmation);

    }

    private static boolean checkArguments(String[] cmdArgs) {

        if (cmdArgs.length < 2){

            System.out.println("Not enough arguments");
            return false;

        }
        return true;

    }

    public static void shopping(){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String customerName = sc.nextLine();

        System.out.println("Welcome " + customerName);

        IShoppingCart shoppingCart = new ShoppingCart(customerName);

        String command;
        String[] cmdArgs;

        do {

            System.out.print("Enter command: ");
            command = sc.nextLine();

            cmdArgs = command.split(" ");

            switch (cmdArgs[0]){

                case "ADD-ITEM": case "REMOVE-ITEM": {

                    if (!checkArguments(cmdArgs)) {
                        break;
                    }

                    String productName = cmdArgs[1];
                    IProduct product = warehouse.stream()
                            .filter(iProduct -> iProduct.getName().equals(productName))
                            .findFirst()
                            .orElse(null);

                    if (product == null) {
                        System.out.println("Product with that name does not exist");
                    } else {

                        if (product.getStockAmount().compareTo(BigDecimal.ZERO) == 0){

                            System.out.println("Stock for that product is empty");
                            break;
                        }

                        try {

                            BigDecimal quantity = new BigDecimal(cmdArgs[2]);

                            if (Objects.equals(cmdArgs[0], "ADD-ITEM")) {

                                if (quantity.compareTo(product.getStockAmount()) > 0){

                                    quantity = product.getStockAmount();

                                }

                                shoppingCart.addItem(product, quantity);
                                System.out.println("Product inserted");
                                break;

                            } else if (Objects.equals(cmdArgs[0], "REMOVE-ITEM")) {

                                System.out.println(shoppingCart.removeItem(product, quantity) ? "Product removed" : "Product is not in cart");

                                break;

                            }

                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.out.println("Not enough arguments");
                            break;
                        }
                    }
                    break;

                }

                case "CHECK-AMOUNT":

                    if (!checkArguments(cmdArgs)) {
                        break;
                    }

                    String productName = cmdArgs[1];
                    IShoppingItem si = shoppingCart.getItems()
                            .stream()
                            .filter(iShoppingItem -> iShoppingItem.getProduct().getName().equals(productName))
                            .findFirst()
                            .orElse(null);

                    System.out.println(si != null ? si.getTotalAmount() : "Product is not in cart");

                    break;

                case "GET-PRODUCT-INFO":

                    if (!checkArguments(cmdArgs)) {
                        break;
                    }

                    String pName = cmdArgs[1];
                    IProduct product = warehouse.stream()
                            .filter(iProduct -> iProduct.getName().equals(pName))
                            .findFirst()
                            .orElse(null);

                    System.out.println(product != null ? product : "No such product");
                    break;

                case "SHOW-PRODUCTS":

                    for (IProduct p : warehouse){

                        System.out.println(p.getName());

                    }
                    break;


                case "GET-CART-ID":

                    System.out.println(shoppingCart.getId());
                    break;

                case "GET-CUSTOMER-NAME":

                    System.out.println(shoppingCart.getUser());
                    break;

                case "EMPTY-CART":

                    shoppingCart.getItems().clear();
                    System.out.println("Cart cleared");
                    break;

                case "CHECK-CART":

                    System.out.println(!shoppingCart.getItems().isEmpty() ? shoppingCart.getItems() : "Cart is empty");

                    break;

                case "CHECK-CART-PRICE":

                    System.out.println("Cart total is: " + shoppingCart.getTotalPrice());
                    break;

                case "CHECK-OUT":

                    if (shoppingCart.getItems().isEmpty()){
                        System.out.println("You bought no products, have a nice day!");
                        return;
                    }

                    System.out.println("You bought:");
                    for (IShoppingItem item : shoppingCart.getItems()){
                        System.out.println(item);
                    }
                    System.out.println("Receipt amount is: " + shoppingCart.getTotalPrice());

                    BigDecimal bd;
                    boolean state = true;

                    do {

                        System.out.print("Enter your payment amount: ");
                        String payment = sc.nextLine();

                        try{

                            bd = new BigDecimal(payment);

                            int result = bd.compareTo(shoppingCart.getTotalPrice());

                            switch (result) {
                                case 0 -> {
                                    System.out.println("Payment successful, goodbye!");
                                    updateStock(shoppingCart.getItems());
                                    state = false;
                                }
                                case 1 -> {
                                    System.out.println("Payment successful, your change is " + bd.subtract(shoppingCart.getTotalPrice()) + ", goodbye!");
                                    updateStock(shoppingCart.getItems());
                                    state = false;
                                }
                                default -> System.out.println("Payment unsuccessful");
                            }

                        }catch (NumberFormatException e){

                            System.out.println("Invalid payment input");

                        }

                    }while (state);

                    return;

                case "EXIT":
                    break;

                default:
                    System.out.println("Unrecognized command");
                    break;

            }

        }while(!Objects.equals(cmdArgs[0], "EXIT"));

    }

    private static void updateStock(List<IShoppingItem> items) {

        for (IShoppingItem item : items){

            IProduct product = warehouse.stream()
                    .filter(iProduct -> iProduct.getName().equals(item.getProduct().getName()))
                    .findFirst()
                    .orElse(null);

            int index = warehouse.indexOf(product);

            if (product != null){
                product.updateQuantity(item.getQuantity());
                warehouse.set(index, product);
            }

        }


    }

}