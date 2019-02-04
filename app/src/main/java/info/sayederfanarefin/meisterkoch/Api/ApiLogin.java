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

import info.sayederfanarefin.meisterkoch.commons.Constants;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.sayederfanarefin.meisterkoch.model.category;
import info.sayederfanarefin.meisterkoch.model.post;
import info.sayederfanarefin.meisterkoch.model.users;

@EBean
public class ApiLogin {


    @RootContext
    Context context;
    private FirebaseAuth auth;

    public DatabaseReference rootRef;


    public ApiLogin() {
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            EventBus.getDefault().post(null);
                        } else {

                            DatabaseReference usersRef = rootRef.child(Constants.dbUserLocation+ "/" + auth.getCurrentUser().getUid());
                            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    //  HashMap<String, users> yourData = (HashMap<String, users>) dataSnapshot.getValue();
                                    users u = (users) dataSnapshot.getValue(users.class);
                                    u.setUid(auth.getCurrentUser().getUid());
                                    EventBus.getDefault().post(u);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    EventBus.getDefault().post(null);
                                }
                            });



                        }
                    }
                });
    }

    public void signup(final String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            users u = new users();
                            u.setEmail(auth.getCurrentUser().getEmail());
                            u.setUid(auth.getCurrentUser().getUid());
                            u.setUsername(name);
                            EventBus.getDefault().post(u);

                        } else {
                            // If sign in fails, display a message to the user.
                            EventBus.getDefault().post(null);
                        }
                    }
                });
    }



}