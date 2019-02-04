package info.sayederfanarefin.meisterkoch.ui.firstFragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import info.sayederfanarefin.meisterkoch.Api.ApiTimeline;
import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.adapters.TimelineFirebaseAdapter;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.model.post;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_time_line)
public class TimeLineFragment extends CoreFragment {

    @Bean
    ApiTimeline apiTimeline;

    @ViewById
    RecyclerView recyclerViewTimeLine;
    final List<post> postsList= new ArrayList<post>();
    TimelineFirebaseAdapter timelineFirebaseAdapter;
    private LinearLayoutManager linearLayoutManager;

    public TimeLineFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {
        linearLayoutManager =new LinearLayoutManager(getContext());
//        linearLayoutManager.setStackFromEnd(true);
//        linearLayoutManager.setReverseLayout(true);

        recyclerViewTimeLine.setLayoutManager(linearLayoutManager);
        recyclerViewTimeLine.setItemAnimator(new DefaultItemAnimator());
        apiTimeline.getTimeline();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimelineRecivedEvent(final post[] posts) {

        LOG("=====recived crap=====");
        if (posts !=null && posts.length != 0){
            LOG("=====recived crap if=====");
            for (int i=0; i < posts.length; i++ ){
                postsList.add( posts[i]);
            }
            timelineFirebaseAdapter = new TimelineFirebaseAdapter(postsList, getContext());
            recyclerViewTimeLine.setAdapter(timelineFirebaseAdapter);

        }else{
            LOG("=====recived crap else=====");
        }
    };

}
