package info.sayederfanarefin.meisterkoch.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.sayederfanarefin.meisterkoch.R;

public class TimelineViewHolder extends RecyclerView.ViewHolder {
    final TextView name;
    final TextView mood;
    String uid;


    public TimelineViewHolder(final View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textViewRecipeCategory);
        mood = itemView.findViewById(R.id.textViewRecipeTimeToCook);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void name(String namee) {
        this.name.setText(namee);
    }

    public void mood(String mood) {
        this.mood.setText(mood);
    }




}