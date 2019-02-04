package info.sayederfanarefin.meisterkoch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

//import com.firebase.ui.auth.AuthUI;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.adapters.DrawerListAdapter;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.core.CoreActivity;
import info.sayederfanarefin.meisterkoch.ui.addRecipe.AddRecipeActivity_;
import info.sayederfanarefin.meisterkoch.ui.authentication.AuthenticationActivity_;
import info.sayederfanarefin.meisterkoch.ui.firstFragment.LikedRecipesFragment_;
import info.sayederfanarefin.meisterkoch.ui.firstFragment.MyProfileFragment_;
import info.sayederfanarefin.meisterkoch.ui.firstFragment.TimeLineFragment_;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by erfan on 10/5/18.
 */

@EActivity(R.layout.activity_first)
@Fullscreen
public class FirstActivity extends CoreActivity {



    @ViewById
    Toolbar toolbar;


    @ViewById
    TabLayout tabLayout;

    private ArrayList<String> navigationItems;
    private int[] pageIcon = {
            R.drawable.home,
            R.drawable.like,
            R.mipmap.white,
            R.drawable.my_recipes,
            R.drawable.profile
    };

    private DrawerListAdapter drawerListAdapter;

    @Bean
    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

    }

    @AfterViews
    void afterViews() {
        getSupportActionBar().hide();

        redirectToAuthenticateActivityIfNeeded();
       // loadFragment(FirstFragment_.builder().build());

        initializeViews();
//        userRef.keepSynced(true);
    }

    public void redirectToAuthenticateActivityIfNeeded(){
        if (!checkUserIfAuthenticated()) {
            AuthenticationActivity_.intent(this).start();
            finish();
        }
    }



    private void initializeViews(){


        for (int i = 0; i < pageIcon.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(pageIcon[i]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               // viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:

                        loadFragment(TimeLineFragment_.builder().build());
                        break;
                    case 1:

                        loadFragment(LikedRecipesFragment_.builder().build());
                        break;
                    case 2:

                       // loadFragment(ChatFragment_.builder().build());
                        break;
                    case 3:

                        // loadFragment(ChatFragment_.builder().build());
                        break;
                    case 4:

                        loadFragment(MyProfileFragment_.builder().build());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        loadFragment(LikedRecipesFragment_.builder().build());

        //viewPager.setCurrentItem(0);



//        if(sharedPrefs.getUserFromSharedPref() != null && sharedPrefs.getUserFromSharedPref().getProfilePicLocation() != null){
//            Glide.with(imageViewNavProfilePic.getContext())
//                    .load(sharedPrefs.getUserFromSharedPref().getProfilePicLocation())
//                    .bitmapTransform(new CropCircleTransformation(this))
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(imageViewNavProfilePic);
//        }
//
//        textViewNavUserName.setText(sharedPrefs.getUserFromSharedPref().getUserName());
//        if(sharedPrefs.getUserFromSharedPref() != null && sharedPrefs.getUserFromSharedPref().getMood() != null){
//            textViewNavUserMood.setText(sharedPrefs.getUserFromSharedPref().getMood());
//        }

    }

//    private void logout(){
//        AuthUI.getInstance().signOut(this);
//        sharedPrefs.saveUserInSharedPref(null);
//        AuthenticationActivity_.intent(this).start();
//        finish();
//    }

    @Click
    void fab(){
        AddRecipeActivity_.intent(this).start();
    }

}
