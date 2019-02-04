package info.sayederfanarefin.meisterkoch.ui.firstFragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.adapters.StableArrayAdapter;
import info.sayederfanarefin.meisterkoch.commons.Constants;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.core.CoreFirebaseFragment;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.model.users;
import info.sayederfanarefin.meisterkoch.ui.FirstActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static info.sayederfanarefin.meisterkoch.commons.Commons.dateToString;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_my_profile)
public class MyProfileFragment extends CoreFirebaseFragment {

    @ViewById
    ImageView profilePictureUpload;
    @ViewById
    ImageButton buttonTakeSnap;
    @ViewById
    RelativeLayout contextMenu;
    @ViewById
    ListView listContextMenu;
    @ViewById
    DecoView dynamicArcView;

    @ViewById
    TextView textViewUserLocation;

    @ViewById
    TextView textViewUserName;

    @Bean
    SharedPrefs sharedPrefs;

    private File destination;
    private String imagePath;
    private boolean uploading = false;
    private boolean animateFlag = false;
    private static final int REQUEST_IMAGE = 100;

    private users user;

    public MyProfileFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {

        user = sharedPrefs.getUserFromSharedPref();
        if(user != null){

            usersRef = rootRef.child(Constants.dbUserLocation+ "/" + user.getUid());
        }

        showPermissionCamera();
        showPermissionWriteExternalStorage();

        loadProfilePicture(sharedPrefs.getUserFromSharedPref().getProfileImageUrl());
        textViewUserLocation.setText(sharedPrefs.getUserFromSharedPref().getUid());
        textViewUserName.setText(sharedPrefs.getUserFromSharedPref().getUsername());

    }



    @Click
    void buttonTakeSnap() {
        showContextMenu();
    }

    @Click
    void closeContextMenu() {
        contextMenu.setVisibility(View.GONE);
    }

    private void showContextMenu() {
        contextMenu.setVisibility(View.VISIBLE);
        String[] values = new String[]{"Upload from Gallery", "Take a picture", "View Picture"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, list);

        listContextMenu.setAdapter(adapter);
        listContextMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                if (item.equals("Upload from Gallery")) {
                    upload();
                } else if (item.equals("Take a picture")) {
                    takePicture();
                } else {

                }

                contextMenu.setVisibility(View.GONE);
            }

        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uploadImage(data.getData());
        }

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            uploadImage(Uri.parse(imagePath));
        }

    }

    private void uploadImage(Uri uri) {
        try {
            uploading = true;
            animateFlag = true;

            animate();
            showSnachBar("Uploading Image...");
            InputStream stream = new FileInputStream(imagePath);
            StorageReference riversRef = storageRef.child("profile_images/" + uri.getLastPathSegment()); //.child(uniqueId + "/profile_pic");//mStorage.child(imageLocationId);
            UploadTask uploadTask = riversRef.putStream(stream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    showSnachBar("Upload failed");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            addImageToProfile(downloadUrl.toString());
                        }
                    });

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void uploadCompleted(){
        uploading = false;
        animateFlag = false;

        dynamicArcView.setVisibility(View.GONE);
    }

    private void loadProfilePicture(String imageLocation){

        dynamicArcView.setVisibility(View.VISIBLE);
        buttonTakeSnap.setVisibility(View.GONE);
        animateFlag = true;

        animate();
        Glide.with(profilePictureUpload.getContext())
                .load(imageLocation)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        animateFlag = false;
                        buttonTakeSnap.setVisibility(View.VISIBLE);
                        dynamicArcView.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                       animateFlag = false;
                        buttonTakeSnap.setVisibility(View.VISIBLE);
                        dynamicArcView.setVisibility(View.GONE);
                        LOG("Resource ready");

                        return false;
                    }
                })
//                .error(R.drawable.com_facebook_profile_picture_blank_portrait)
//                .fallback(R.drawable.com_facebook_profile_picture_blank_portrait)
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .into(profilePictureUpload);
    }

    public void addImageToProfile(final String imageLocation) {
        usersRef.child(Constants.dbUserUserPhoto).setValue(imageLocation).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        user.setProfileImageUrl(imageLocation);
                        sharedPrefs.saveUserInSharedPref(user);
                        loadProfilePicture(imageLocation);

                        uploadCompleted();

                    }
                }
        );

    }


    private boolean upload() {


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
        return true;
    }

    private boolean takePicture() {

        Intent intent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


        File imagePath = new File(getContext().getFilesDir(), "public");
        if (!imagePath.exists()) imagePath.mkdirs();
        String name = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
        destination = new File(imagePath, name + "tmp.jpg");

        this.imagePath = destination.getAbsolutePath();

        Uri imageUri = FileProvider.getUriForFile(getContext(), "info.sayederfanarefin.meisterkoch.provider", destination);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent2, REQUEST_IMAGE);


        return true;
    }


    private void animate() {
        SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor(getString(R.string.animation_circle_color_1)))
                .setRange(0, 50, 0)
                .build();

        final SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor(getString(R.string.animation_circle_color_2)))
                .setRange(0, 50, 0)
                .build();
        int backIndex = dynamicArcView.addSeries(seriesItem);
        final int series1Index = dynamicArcView.addSeries(seriesItem2);

        if (animateFlag) {
            dynamicArcView.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex)
                    .setListener(new DecoEvent.ExecuteEventListener() {
                        @Override
                        public void onEventStart(DecoEvent decoEvent) {
                        }

                        @Override
                        public void onEventEnd(DecoEvent decoEvent) {

                            if (animateFlag) {
                                dynamicArcView.addEvent(new DecoEvent.Builder(50)
                                        .setIndex(series1Index)
                                        .setListener(new DecoEvent.ExecuteEventListener() {
                                            @Override
                                            public void onEventStart(DecoEvent decoEvent) {
                                            }

                                            @Override
                                            public void onEventEnd(DecoEvent decoEvent) {
                                                if (animateFlag) {
                                                    animate();
                                                }
                                            }
                                        })
                                        .build());
                            }
                        }
                    })
                    .build());
        }
    }

    @Click
    void buttonSettings(){
            Intent i = new Intent(getActivity(), Settings.class);
            startActivity(i);
    }


}
