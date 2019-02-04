package info.sayederfanarefin.meisterkoch.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreActivity;

/**
 * Created by erfan on 10/5/18.
 */

@EActivity(R.layout.content_profile_activity)
@Fullscreen
public class ProfileActivity extends CoreActivity {


    @AfterViews
    void afterViews() {
        getSupportActionBar().hide();
        loadFragment(ProfileFragment_.builder().build());

    }

}
