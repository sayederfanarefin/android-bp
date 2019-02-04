package info.sayederfanarefin.meisterkoch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.model.post;
import info.sayederfanarefin.meisterkoch.viewHolders.TimelineViewHolder;


/**
 * Created by erfan on 9/14/17.
 */

public class TimelineFirebaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<post> friendsList;
    private Context context;

    public TimelineFirebaseAdapter(List<post> friendsList, Context context) {
        this.friendsList = friendsList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post, parent, false);
            return new TimelineViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TimelineViewHolder viewHolder_friends = (TimelineViewHolder) holder;

                post post = friendsList.get(position);

                viewHolder_friends.mood(post.getTitle());
                viewHolder_friends.name(post.getCookingTime());


    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

}