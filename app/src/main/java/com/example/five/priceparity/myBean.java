package com.example.five.priceparity;

/**
 * An immutable class for show the game information on the front page,
 * including name, image url, price.
 * Only has getter for this information.
 */
public class myBean {

    private final String name;
    private final String image;
    private final String price;


    // Abstraction Function
    // name -- the name of the game
    // price -- the min price of the game
    // imageurl -- the image link of the games
    // Representation invariant
    // name != null && length > 0
    // Safety from rep exposure:
    // All fields are private final
    // Can't be changed since init

    public myBean(String name, String price, String imageUrl){
        this.image = imageUrl;
        this.name = name;
        this.price = price;
    }

    /**
     * To get the name of the game
     *
     * @return the name String of the game
     */
    public String getName() {
        return name;
    }

    /**
     * To get the price of the game
     *
     * @return the price of the game
     */
    public String getPrice() {
        return price;
    }

    /**
     * To get the imageurl of the game
     *
     * @return the image url of the game
     */
    public String getImageUrl() {
        return image;
    }
}
