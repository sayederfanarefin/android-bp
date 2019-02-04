package info.sayederfanarefin.meisterkoch.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.model.category;

public class CategoriesAdapter extends ArrayAdapter<category> {


    private final Context context;
    private final category[] values;

    public CategoriesAdapter(Context context, category[] values) {
        super(context, R.layout.item_category, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_category, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewTitle);
        textView.setText(values[position].getName());
        Log.v("=====xxxxx=====",values[position].getName() );

        return rowView;
    }
}
