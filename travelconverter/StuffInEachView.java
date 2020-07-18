package com.example.android.travelconverter;

public class StuffInEachView {

    private String itemOne;

    private String itemTwo;

    private String imageURL;


    public StuffInEachView(String itemOneWrd, String itemTwoWrd, String imageLink)
    {
        itemOne = itemOneWrd;
        itemTwo = itemTwoWrd;
        imageURL = imageLink;
    }

    public String getItemOne() { return  itemOne; }

    public String getItemTwo() { return itemTwo; }

    public String getImageURL() { return imageURL; }
}
