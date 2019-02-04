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
public class ApiRecipe {


    @RootContext
    Context context;
    private FirebaseAuth auth;

    public DatabaseReference rootRef;


    public ApiRecipe() {
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

    }


    public void addRecipe(final post recipe, String userUid) {
        DatabaseReference newRef = rootRef.child(Constants.dbPostLocation).child(userUid).push();
        newRef.setValue(recipe);
        EventBus.getDefault().post(recipe);
    }

}