package info.sayederfanarefin.meisterkoch.ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.adapters.StableArrayAdapter;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;

import static info.sayederfanarefin.meisterkoch.commons.Commons.dateToString;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_profile)
public class ProfileFragment extends CoreFragment {

    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView toolbarTitle;
    @ViewById
    RelativeLayout editphone;
    @ViewById
    RelativeLayout editemail;
    @ViewById
    RelativeLayout editpassword;
    @ViewById
    RelativeLayout editqr;
    @ViewById
    RelativeLayout editid;
    @ViewById
    RelativeLayout  time_line_relative_layout;
    @ViewById
    RelativeLayout profile_activity_friends_Layout;
    @ViewById
    TextView phone_display;
    @ViewById
    TextView email_display ;
    @ViewById
    TextView id_display ;
    @ViewById
    TextView profile_name_textview ;
    @ViewById
    TextView status_textview ;

    @ViewById
    ImageView profilePicture;
    @ViewById
    ImageView coverPic;

    @ViewById
    ImageButton imageButton;
    @ViewById
    ImageButton floating_change_cover;
    @ViewById
    ImageButton status_edit;
    @ViewById
    RelativeLayout contextMenu;
    @ViewById
    ListView listContextMenu;
    private int proCoverPic = 0;

    private File destination;
    private String imagePath;
    private boolean uploading = false;
    private boolean animateFlag = false;
    private static final int REQUEST_IMAGE = 100;

    public ProfileFragment() {
        //Mandatory default constructor

    }

    @AfterViews
    void afterViews() {
        showPermissionCamera();
        showPermissionWriteExternalStorage();


    }

    @Click
    void imageButton() {
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
        Uri imageUri = FileProvider.getUriForFile(getContext(), "info.sayederfanarefin.chat.provider", destination);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent2, REQUEST_IMAGE);
        return true;
    }

}
