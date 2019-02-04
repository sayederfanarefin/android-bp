package info.sayederfanarefin.meisterkoch.core;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.commons.TypefaceUtil;
import info.sayederfanarefin.meisterkoch.config.Config;
import info.sayederfanarefin.meisterkoch.dialog.LockProgressDialog;
import info.sayederfanarefin.meisterkoch.helpers.Validate;
import info.sayederfanarefin.meisterkoch.log.Tracer;

import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

//import info.sayederfanarefin.chat.ui.authentication.AuthenticationActivity_;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity
public class CoreActivity extends AppCompatActivity {

    protected final String TRACE_TAG = Config.TRACE_TAG + "CoreActivity";

    protected LockProgressDialog dialog;

    protected String tag;

    protected FirebaseAuth mFirebaseAuth;
   // protected DatabaseReference userRef;
    protected FirebaseAuth.AuthStateListener mAuthStateListener;

    // private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Poppins-Regular.ttf");

        Tracer.v(this.getClass().getSimpleName() + " started");

        setTag(this.getClass().getSimpleName());
        getSupportActionBar().hide();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void displayLockProgress() {
        FragmentManager fm = getSupportFragmentManager();
        if (dialog != null) dismissLockProgress();
        dialog = new LockProgressDialog();
        dialog.show(fm, "LockProgressDialog");
    }

    public void dismissLockProgress() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * Load fragment by replacing all previous fragments
     * @param fragment
     */
    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // clear back stack
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.replace(R.id.mainView, fragment, "main");
        fragmentManager.popBackStack();
        // TODO: we have to allow state loss here
        // since this function can get called from an AsyncTask which
        // could be finishing after our app has already committed state
        // and is about to get shutdown.  What we *should* do is
        // not commit anything in an AsyncTask, but that's a bigger
        // change than we want now.
        t.commitAllowingStateLoss();
    }

    /**
     * Load fragment by replacing all previous fragments and assigning tag
     * @param fragment, tag
     */
    public void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // clear back stack
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.replace(R.id.mainView, fragment, tag);
        t.addToBackStack(null);

        // The following pop command was creating problems when loading the
        // required fragment. Just on pushing the fragment to stack, it was
        // being popped and hence, the search on view objects was creating an
        // error.
        // fragmentManager.popBackStack();

        // TODO: we have to allow state loss here
        // since this function can get called from an AsyncTask which
        // could be finishing after our app has already committed state
        // and is about to get shutdown.  What we *should* do is
        // not commit anything in an AsyncTask, but that's a bigger
        // change than we want now.
        t.commitAllowingStateLoss();
    }

    /**
     * Load Fragment on top of other fragments
     * @param fragment
     */
    public void loadChildFragment(Fragment fragment) {
        Validate.notNull(fragment);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainView, fragment, "main")
                .addToBackStack(null)
                .commit();
    }

    /**
     * Load Fragment on top of other fragments by removing last fragment
     * @param fragment, flag
     */
    public void loadChildFragment(Fragment fragment, boolean flag) {
        Validate.notNull(fragment);
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainView, fragment, "main")
                .addToBackStack(null)
                .commit();
    }

    public void popChildFragment(Fragment fragment) {
        Validate.notNull(fragment);
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        // if (databaseHelper == null) databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
//        if (databaseHelper != null) {
//            OpenHelperManager.releaseHelper();
//            databaseHelper = null;
//        }
    }

    @Subscribe
    public void onGenericEvent(Object event) {
        // DO NOT WRITE CODE
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

//    public DatabaseHelper getHelper() {
//        if (databaseHelper == null) databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
//        return databaseHelper;
//    }

    public void showSnachBar(String message){
        final Snackbar sb =  Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setDuration(5000);
        sb.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.dismiss();
            }
        });
        View view = sb.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        sb.show();
    }

    public boolean checkUserIfAuthenticated(){
        mFirebaseAuth = FirebaseAuth.getInstance();

        if ( mFirebaseAuth.getCurrentUser() == null) {
            return false;
        }else{
            return true;
        }
    }

}