package info.sayederfanarefin.meisterkoch.ui.authentication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreActivity;
import info.sayederfanarefin.meisterkoch.event.MessageEvent;

/**
 * Created by erfan on 10/5/18.
 */

@EActivity(R.layout.activity_authentication)
public class AuthenticationActivity extends CoreActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @AfterViews
    void afterViews() {
        getSupportActionBar().hide();
        loadFragment(AuthenticationFirstFragment_.builder().build(), "firstFr");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        showSnachBar("activity result ");
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post(new MessageEvent(requestCode, resultCode ,data));
  //      getFragmentManager().findFragmentByTag("firstFr").onActivityResult(requestCode, resultCode, data);
    }
}
