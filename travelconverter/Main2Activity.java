package com.example.android.travelconverter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

/*
  Created by |C.E.P.H.A.S|K.E.V.I.N|
*/

    public JSONArray restaurantArray = null;
    public double latitude = 51.574530;
    public double longitude = 0.119570;
    public String userKey = "e5b80df84fcbbaf1c169e081517f891a";//Hardcoded for now, will move to strings resource folder
    //private String URL = "https://developers.zomato.com/api/v2.1/search?lat=51.574530&lon=0.119570";
    private String url3 = "https://developers.zomato.com/api/v2.1/search?count=10&lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude)+"&sort=rating";//Sort by rating
    private String url2 = "https://developers.zomato.com/api/v2.1/categories";
    private String url = "https://developers.zomato.com/api/v2.1/search?count=5";




    private void getResponse()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { AccessDataFromJson(response); Log.e("New Request", "Data received!");}
                },
                new Response.ErrorListener() { @Override public void onErrorResponse(VolleyError error) { Log.e("Check Error", "Error"); }})

        {@Override public byte[] getBody() { return new byte[]{}; }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");
                map.put("user_key", userKey);
                return map;
            }
        };queue.add(request);
    }


    public void extractJSONRestaurantData(JSONArray restaurantArray, ArrayList<StuffInEachView> itemLayout) throws JSONException
    {
        if (restaurantArray.length() > 0)
        {

            for (int i = 0; i < restaurantArray.length(); i++)
            {
                JSONObject restaurant = restaurantArray.getJSONObject(i);
                JSONObject rs = restaurant.getJSONObject("restaurant");//Initializing the JSONObject that holds individual restaurant information
                JSONObject lcn = rs.getJSONObject("location");

                String RestaurantName = rs.getString("name"), RestaurantLocation = lcn.getString("address");//Extract information about this restaurant
                String restaurantImageURL = rs.getString("featured_image");//Get the restaurant image
                itemLayout.add(new StuffInEachView(RestaurantName, RestaurantLocation, restaurantImageURL));
            }
            setIntentToEachView(itemLayout);
        }
    }


    public void AccessDataFromJson(String jsonInput)
    {
        ArrayList<StuffInEachView> layoutPattrn = new ArrayList<>();
        if (jsonInput != null)
        {
            try
            {
                JSONObject JsonResponse = new JSONObject(jsonInput);
                restaurantArray = JsonResponse.getJSONArray("restaurants");
                RestaurantDetails.copyRestaurantJSONArray(restaurantArray);
                extractJSONRestaurantData(restaurantArray, layoutPattrn);
            }
            catch (JSONException e) { Log.e("JSONException", "Problem parsing the JSON results", e); }
        }
    }


    public void setIntentToEachView(ArrayList<StuffInEachView> itemLayout)
    {
        ListView listView = findViewById(R.id.list_of_nearby_restaurants);
        final WordAdapter restaurantAdapter = new WordAdapter(this, itemLayout);
        // Get a reference to the ListView, and attach the adapter to the listView.
        listView.setAdapter(restaurantAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent RestaurantDetailsScreen = new Intent(Main2Activity.this, RestaurantDetails.class);
                startActivity(RestaurantDetailsScreen);

                RestaurantDetails.getSelectedRestaurantIndex(position);
            }
        });
    }

    public static String getStringResourceByName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.setTitle(getStringResourceByName(Main2Activity.this,MainActivity.user_language+"_RestaurantsNearMe"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResponse();//DATA IS ONLY RELOADED UPON BUTTON PRESS!
            }
        });

        getResponse();
    }
}
