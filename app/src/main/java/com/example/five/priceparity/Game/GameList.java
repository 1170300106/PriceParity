package com.example.five.priceparity.Game;

/**
 * An immutable class for show the game information on the front page,
 * including name, image url, price.
 * Only has getter for this information.
 */
public class GameList {
    private final String name;
    private final double price;
    private final String imageUrl;

    // Abstraction Function
    // name -- the name of the game
    // price -- the min price of the game
    // imageurl -- the image link of the games
    // Representation invariant
    // name != null && length > 0
    // Safety from rep exposure:
    // All fields are private final
    // Can't be changed since init

    public GameList(String name, double price, String imageUrl){
        this.imageUrl = imageUrl;
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
    public double getPrice() {
        return price;
    }

    /**
     * To get the imageurl of the game
     *
     * @return the image url of the game
     */
    public String getImageUrl() {
        return imageUrl;
    }
}
