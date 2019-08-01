package com.example.five.priceparity;

import java.util.List;

/**
 *  An immutable class for show the game details in the show game activity,
 *  including name, contents, image url, comments, purchase url.
 *  Only has getter for this information.
 */
public class Game {
    private String name;
    //private double[] prices = new double[2];
    private String[] prices= new String[2];
    private String content;
    private String image;
    private List<String> comments;
    private String link;

    // Abstraction Function
    // name -- the name of the game
    // prices -- the price array of the game
    // contents -- the introduction of the game
    // imageurl -- the image link of the games
    // comments -- the comments of the game
    // url -- the purchase url of the game
    // Representation invariant
    // prices.length = 2
    // prices[0] = the price on steam
    // prices[1] = the price on Epic
    // Safety from rep exposure:
    // All fields are private final
    // Can't be changed since init

    public Game(String name, String priceSteam, String priceEpic, String contents,
                String imageUrl, List<String> comments, String url){
        this.name = name;
        this.prices[0] = priceSteam;
        this.prices[1] = priceEpic;
        this.content = contents;
        this.image = imageUrl;
        this.comments = comments;
        this.link = url;
    }

    public Game(String name){
        this.name = name;

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
     * To get the prices of the game
     * prices.length = 2
     * prices[0] = the price on steam
     * prices[1] = the price on Epic
     *
     * @return the prices of the game
     */
    public String[] getPrices() {
        return prices;
    }

    /**
     * To get the Contents of the game
     *
     * @return the String of contents
     */
    public String getContents() {
        return content;
    }

    /**
     * To get the imageurl of the game
     *
     * @return the image url of the game
     */
    public String getImageUrl() {
        return image;
    }

    /**
     * To get the comments List
     *
     * @return the List String comments of the game
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * To get the purchase url of the game
     *
     * @return the purchase url of the game
     */
    public String getUrl() {
        return link;
    }
}
