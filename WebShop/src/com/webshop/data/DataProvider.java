package com.webshop.data;

import com.webshop.IProduct;
import com.webshop.models.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<IProduct> getData(){

        Path PATH = Paths.get("res/Fruits.csv");

        List<IProduct> products = new ArrayList<>();

        try(BufferedReader br = Files.newBufferedReader(PATH)) {

            String line = br.readLine();

            while (line != null && !line.isEmpty()){

                String[] att = line.split(";");

                Product product = returnNewProduct(att);

                products.add(product);

                line = br.readLine();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return products;


    }

    private static Product returnNewProduct(String[] att) {

        return new Product(att[0], att[1], att[2], new BigDecimal(att[3]), new BigDecimal(att[4]));

    }


}
