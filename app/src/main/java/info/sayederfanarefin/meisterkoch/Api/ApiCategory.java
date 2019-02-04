package info.sayederfanarefin.meisterkoch.Api;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.sayederfanarefin.meisterkoch.commons.Constants;
import info.sayederfanarefin.meisterkoch.model.category;
import info.sayederfanarefin.meisterkoch.model.post;
import info.sayederfanarefin.meisterkoch.model.users;

@EBean
public class ApiCategory {


    @RootContext
    Context context;
    private FirebaseAuth auth;

    public DatabaseReference rootRef;


    public ApiCategory() {
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    public void getCategories(){

        DatabaseReference mRef = rootRef.child(Constants.dbCategoryLocation);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<category> postsList= new ArrayList<category>();

                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                    postsList.add(imageSnapshot.getValue(category.class));
                    Log.v("=====xxxxx=====", imageSnapshot.getValue(category.class).getName());
                }

                category[] stockArr = new category[postsList.size()];
                stockArr = postsList.toArray(stockArr);

                EventBus.getDefault().post(stockArr);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(null);
            }
        });

    }
}