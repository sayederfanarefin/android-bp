package info.sayederfanarefin.meisterkoch.ui.addRecipe;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wang.avi.AVLoadingIndicatorView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import info.sayederfanarefin.meisterkoch.Api.ApiLogin;
import info.sayederfanarefin.meisterkoch.Api.ApiRecipe;
import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.model.post;
import info.sayederfanarefin.meisterkoch.model.users;


/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_add_recipe)
public class AddRecipeFragment extends CoreFragment {

    private users user;

    @ViewById
    Button buttonAddRecipe;

    @ViewById
    AVLoadingIndicatorView avi;

    @ViewById
    EditText editTextDirections;

    @ViewById
    EditText editTextIngredients;

    @ViewById
    EditText editTextTitle;

    @ViewById
    ImageView imageViewUpload;

    @Bean
    ApiRecipe apiRecipe;

    @Bean
    SharedPrefs sharedPrefs;

    private boolean flag = false;

    public AddRecipeFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {
        user = sharedPrefs.getUserFromSharedPref();
    }


    @Click
    void buttonAddRecipe(){
        if (TextUtils.isEmpty(editTextTitle.getText().toString())) {
            editTextTitle.setError("Please enter password");
            flag = false;
        } else {
            editTextTitle.setError(null);
            flag = true;

            if (TextUtils.isEmpty(editTextIngredients.getText().toString())) {
                editTextIngredients.setError("Please enter password");
                flag = false;
            } else {
                editTextIngredients.setError(null);
                flag = true;

                if (TextUtils.isEmpty(editTextDirections.getText().toString())) {
                    editTextDirections.setError("Please enter password");
                    flag = false;
                } else {
                    editTextDirections.setError(null);
                    flag = true;
                    startAnim();
                    post recipe = new post();
                    recipe.setTitle(editTextTitle.getText().toString());
                    recipe.setIngredients(editTextIngredients.getText().toString());
                    recipe.setDirections( editTextDirections.getText().toString());
                    apiRecipe.addRecipe( recipe,user.getUid() );

                }
            }

        }

    }

    @Click
    void imageViewUpload(){

    }

    void startAnim(){
        avi.setVisibility(View.VISIBLE);
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.setVisibility(View.GONE);
        avi.hide();
        // or avi.smoothToHide();
    }

    @Click
    void imageViewBackButton() {
        getFragmentManager().popBackStack();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddRecipeEvent(post recipe) {
        if (recipe !=null){
            showSnachBar("Recipe Added");
            getFragmentManager().popBackStack();
        }else{
            showSnachBar("Something went wrong, please try again later");
        }

        stopAnim();
    };

}
