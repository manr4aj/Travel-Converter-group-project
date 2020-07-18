package com.example.android.travelconverter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<StuffInEachView> {

    //RequestOptions in order to provide a Crop type for the downloaded image and a backup image when a restaurant image is not available
    public static RequestOptions options = new RequestOptions()
            .centerCrop()
            .error(R.drawable.img_not_available)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public WordAdapter(Activity context, ArrayList<StuffInEachView> itemLayout) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, itemLayout);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        StuffInEachView currentStuff = getItem(position);

        updateViews(listItemView, currentStuff);
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    public void updateViews(View listItemView, StuffInEachView currentStuff)
    {
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.item1);
        nameTextView.setText(currentStuff.getItemOne());

        TextView numberTextView = (TextView) listItemView.findViewById(R.id.item2);
        numberTextView.setText(currentStuff.getItemTwo());

        ImageView imageView = listItemView.findViewById(R.id.restaurant_image);

        Glide.with(getContext()).load(currentStuff.getImageURL())//Using Glide to load image from URL
                .apply(options)
                .into(imageView);
    }
}
