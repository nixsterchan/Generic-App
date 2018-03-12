package com.example.frederic.genericapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;



/**
 * Class to store information about a restaurant menu
 * Created by: Frederick Bernkastel
 */
class RestaurantMenu extends FetchedObject {
    String name;
    URL imageURL;
    ArrayList<MenuItem> menu;

    /**
     * Constructor
     * @param name      Name of restaurant
     * @param imageURL  URL of restaurant's image to be displayed on the app
     */
    RestaurantMenu(String name, String imageURL){
        this.name = name;
        this.menu = new ArrayList<>();
        try {
            this.imageURL = new URL(imageURL);
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to save menu item
     * @param id            Unique id for menu item
     * @param price         Price of item
     * @param name          Name of item
     * @param description   Description of item
     * @param imageURL      Image of item
     */
    void addItem(int id, double price, String name,String description,String imageURL){
        try {
            URL url = new URL(imageURL);
            MenuItem item = new MenuItem(id,price,name,description,url);
            menu.add(item);
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *  Function to help debugging process by printing menu items
     */
    void printMenu(){
        System.out.println(name);
        System.out.println(imageURL);
        for (int i =0;i<menu.size();i++){
            MenuItem item = menu.get(i);
            String s = String.format(Locale.US,"id %d\nprice %.2f\nname %s\ndescription %s",item.id,item.price,item.name,item.description);
            System.out.println(s);
        }
    }

}
class MenuItem {
    int id;
    double price;
    String name;
    String description;
    URL imageURL;
    MenuItem(int id, double price, String name,String description,URL imageURL){
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }

}
