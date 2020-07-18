package com.example.android.travelconverter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;




public class Tab1Fragment extends Fragment {

    public String numberOfUsersString = "";
    public static String selectedRestrnt = "";
    public static String restaurantMenuURL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        Button enter_rating_screen = view.findViewById(R.id.ENTER_RATING_SCREEN);
        enter_rating_screen.setText(Main2Activity.getStringResourceByName(getContext(), MainActivity.user_language + "_ReadReviewsOrRate"));
        enter_rating_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rate = new Intent(getActivity(), UserReviewsDatabase.class);
                startActivity(rate);
            }
        });

        try { setViews(view); }
        catch (JSONException e) { e.printStackTrace(); }
        return view;
    }

    public void setUserLanguage(TextView costString, TextView ratingString, TextView cuisinesString)
    {
        costString.setText(Main2Activity.getStringResourceByName(getContext(),MainActivity.user_language + "_AverageCostForTwo"));
        ratingString.setText(Main2Activity.getStringResourceByName(getContext(),MainActivity.user_language + "_AverageUserRating"));
        cuisinesString.setText(Main2Activity.getStringResourceByName(getContext(),MainActivity.user_language + "_Cuisines"));
        numberOfUsersString = (Main2Activity.getStringResourceByName(getContext(),MainActivity.user_language + "_NumberOfUserRatings"));
    }

    public void setViews(View view) throws JSONException {
        //Get the relevant restaurant JSON Object
        JSONObject selectedRestaurant = (RestaurantDetails.jsonArrayOfRestaurants).getJSONObject(RestaurantDetails.JSONArrayIndex).getJSONObject("restaurant");
        JSONObject restaurantLocation = selectedRestaurant.getJSONObject("location");
        JSONObject restaurantRating = selectedRestaurant.getJSONObject("user_rating");
        restaurantMenuURL = selectedRestaurant.getString("menu_url");
        TextView restaurantName = view.findViewById(R.id.restaurant_name)
                ,restaurantLocationView = view.findViewById(R.id.restaurant_location)
                ,restaurantCostView = view.findViewById(R.id.restaurant_cost)
                ,userRatingView = view.findViewById(R.id.average_user_rating)
                ,cuisinesTxtView = view.findViewById(R.id.cuisines);
        setUserLanguage(restaurantCostView, userRatingView, cuisinesTxtView);

        selectedRestrnt = (selectedRestaurant.getString("name"));
        restaurantName.setText(selectedRestaurant.getString("name"));
        restaurantLocationView.setText(restaurantLocation.getString("address"));
        restaurantCostView.append(": £" + selectedRestaurant.getString("average_cost_for_two"));
        userRatingView.append(": " + restaurantRating.getString("aggregate_rating") + " ," + numberOfUsersString + ": " + restaurantRating.getString("votes"));
        cuisinesTxtView.append(": " +selectedRestaurant.getString("cuisines"));
    }

}