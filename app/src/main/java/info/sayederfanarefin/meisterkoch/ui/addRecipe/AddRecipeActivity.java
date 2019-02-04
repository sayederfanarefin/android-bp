package info.sayederfanarefin.meisterkoch.ui.addRecipe;

import android.os.Handler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreActivity;

@EActivity(R.layout.activity_add_recipe)
@Fullscreen
public class AddRecipeActivity extends CoreActivity {

    @AfterViews
    void afterViews() {

        loadFragment(AddRecipeFragment_.builder().build(), "firstFr");
    }


}
