package sg.edu.rp.c346.id20011280.foodigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Food> FoodList;

    public CustomAdapter(Context context, int resource, ArrayList<Food> objects)
    {
        super(context,resource,objects);

        this.parent_context = context;
        this.layout_id = resource;
        this.FoodList = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvDesc = rowView.findViewById(R.id.textViewDescription);
        TextView tvPrice = rowView.findViewById(R.id.textViewPrice);

        // Obtain the Android Version information based on the position
        Food currentFood = FoodList.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentFood.getName());
        tvDesc.setText(currentFood.getDescription());
        tvPrice.setText(String.valueOf(currentFood.getPrice()));

        return rowView;
    }
}
