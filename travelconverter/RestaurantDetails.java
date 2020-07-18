package com.example.android.travelconverter;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
  Created by |C.E.P.H.A.S|K.E.V.I.N|
*/

public class RestaurantDetails extends AppCompatActivity {

    public static  JSONArray jsonArrayOfRestaurants = null;
    public static int JSONArrayIndex = 0;

    SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager viewPager;

    public static void copyRestaurantJSONArray(JSONArray jsonArray) { jsonArrayOfRestaurants = jsonArray; }
    public static void getSelectedRestaurantIndex(int i){JSONArrayIndex = i;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        //Makes screen status bar completely transparent, for presenting restaurant info ;)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        try { updatePageWithDetails(); }
        catch (JSONException e) { e.printStackTrace(); }
    }

    public void setRestaurantImage(String imageURL, ImageView imageView)
    {
        Glide.with(RestaurantDetails.this).load(imageURL)//Using Glide to load image from URL
                .apply(WordAdapter.options)
                .into(imageView);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new Tab1Fragment(), Main2Activity.getStringResourceByName(RestaurantDetails.this, MainActivity.user_language + "_INFO"));
        sectionsPageAdapter.addFragment(new Tab2Fragment(), Main2Activity.getStringResourceByName(RestaurantDetails.this, MainActivity.user_language + "_PRICE"));
        viewPager.setAdapter(sectionsPageAdapter);
    }


    public void updatePageWithDetails() throws JSONException
    {
        JSONObject selectedRestaurant = jsonArrayOfRestaurants.getJSONObject(JSONArrayIndex).getJSONObject("restaurant");//Get the relevant restaurant JSON Object
        ImageView imageView = findViewById(R.id.restrnt_image);
        setRestaurantImage(selectedRestaurant.getString("featured_image"),imageView);
    }
}