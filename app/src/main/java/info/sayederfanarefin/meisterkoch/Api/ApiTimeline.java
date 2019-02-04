package info.sayederfanarefin.meisterkoch.Api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.sayederfanarefin.meisterkoch.commons.Constants;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.model.category;
import info.sayederfanarefin.meisterkoch.model.post;

@EBean
public class ApiTimeline {


    @RootContext
    Context context;

    @Bean
    SharedPrefs sharedPrefs;

    public DatabaseReference rootRef;

    final List<post> postsList = new ArrayList<post>();
    final List<HashMap<String, String>> postsKeys = new ArrayList<HashMap<String, String>>();


    public ApiTimeline() {

        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    public void getTimeline() {


        DatabaseReference mRef = rootRef.child(Constants.dbTimelineLocation).child(sharedPrefs.getUserFromSharedPref().getUid());

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //  postsList.add(imageSnapshot.getValue(String.class));
                    String postId = dataSnapshot1.getKey();
                    String posterId = dataSnapshot1.child("poster_uid").getValue(String.class);

                    HashMap<String, String> myMap = new HashMap<String, String>();
                    myMap.put("posterId", posterId);
                    myMap.put("postId", postId);
                    postsKeys.add(myMap);
                }
                count = 0;
                getPost(postsKeys.get(0).get("posterId"), postsKeys.get(0).get("postId"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(null);
            }
        });

    }

    int count = 0;

    public void getPost(String posterId, final String postId) {
        rootRef.child(Constants.dbPostLocation).child(posterId).child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, post> result = (HashMap<String, post>) dataSnapshot.getValue();


//                post p = (post) result.get(postId);
//                Log.v("======", p.getTitle());

               // post p = dataSnapshot.getValue(String.class);
               // postsList.add(p);
                count++;
                if (count < postsKeys.size()) {
                    getPost(postsKeys.get(count).get("posterId"), postsKeys.get(count).get("postId"));
                } else {

                    post[] stockArr = new post[postsList.size()];
                    stockArr = postsList.toArray(stockArr);
                    EventBus.getDefault().post(stockArr);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("=====vvv", "psot ref call cancelled");
            }
        });
    }
}