package info.sayederfanarefin.meisterkoch.core;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.Subscribe;

import info.sayederfanarefin.meisterkoch.commons.Constants;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.log.Tracer;
import info.sayederfanarefin.meisterkoch.model.users;


@EFragment
public abstract class CoreFirebaseFragment extends CoreFragment {

    public DatabaseReference rootRef;
    public StorageReference storageRef;



    public CoreFirebaseFragment(){

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        rootRef = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        Tracer.d(this.getClass().getSimpleName() + " onStop()");
        super.onStop();

    }

    @Subscribe
    public void onGenericEvent(Object event) {
        // DO NOT WRITE CODE
    }

}
