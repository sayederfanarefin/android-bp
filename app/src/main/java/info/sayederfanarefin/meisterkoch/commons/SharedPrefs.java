package info.sayederfanarefin.meisterkoch.commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.model.Settings;
import info.sayederfanarefin.meisterkoch.model.users;

@EBean
public class SharedPrefs {

    @RootContext
    Context context;

    public SharedPrefs(){

    }

    public void saveUserInSharedPref(users currentUser){
        Activity activity = (Activity)context;

        SharedPreferences mPrefs =  activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentUser);

        prefsEditor.putString(activity.getString(R.string.sharedPrefCurrentUser), json);
        prefsEditor.commit();
    }

    public users getUserFromSharedPref(){
        Activity activity = (Activity)context;
        Gson gson = new Gson();
        SharedPreferences mPrefs = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String json = mPrefs.getString(activity.getString(R.string.sharedPrefCurrentUser), "");
        return gson.fromJson(json, users.class);
    }

    public void saveStringInSharedPref(String tag, String text){
        Activity activity = (Activity)context;
        SharedPreferences mPrefs = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putString(tag, text);
        prefsEditor.commit();
    }

    public Settings getSettingsFromSharedPref(){
        Activity activity = (Activity)context;
        Gson gson = new Gson();
        SharedPreferences mPrefs = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String json = mPrefs.getString(activity.getString(R.string.sharedPrefSettings), "");
        return gson.fromJson(json, Settings.class);
    }

    public void saveSettingsInSharedPref(Settings settings){
        Activity activity = (Activity)context;
        SharedPreferences mPrefs = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(settings);
        prefsEditor.putString(activity.getString(R.string.sharedPrefSettings), json);
        prefsEditor.commit();
    }

    public String getStringFromSharedPref(String tag, String def) {
        Activity activity = (Activity)context;

        SharedPreferences mPrefs =activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return mPrefs.getString(tag, def);

    }
//    public void saveFirebaseUserInSharedPref(FirebaseUser currentUser) {
//        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = mPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(currentUser);
//        prefsEditor.putString(context.getString(R.string.sharedPrefCurrentFirebaseUser), json);
//        prefsEditor.commit();
//    }
//
//    public FirebaseUser getFirebaseUserFromSharedPref() {
//        Gson gson = new Gson();
//        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
//        String json = mPrefs.getString(context.getString(R.string.sharedPrefCurrentFirebaseUser), "");
//        return gson.fromJson(json, FirebaseUser.class);
//    }


    public void defaultSettings(){

        info.sayederfanarefin.meisterkoch.model.Settings settings = new info.sayederfanarefin.meisterkoch.model.Settings();
        settings.setVibrate(true);
        settings.setSound(true);
        settings.setNewFriendsNotification(true);
        settings.setMessageNotification(true);
        saveSettingsInSharedPref(settings);
    }
}
