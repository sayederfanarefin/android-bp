package info.sayederfanarefin.meisterkoch.ui.firstFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import info.sayederfanarefin.meisterkoch.Api.ApiCategory;
import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.adapters.CategoriesAdapter;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.model.category;
import info.sayederfanarefin.meisterkoch.Api.ApiLogin;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_liked_recipes)
public class LikedRecipesFragment extends CoreFragment {

    @ViewById
    RecyclerView recyclerViewLikedRecipes;

    @ViewById
    ListView categoriesList;

    @ViewById
    LinearLayout showCategories;

    @Bean
    ApiCategory apiCategory;

    private FirebaseRecyclerAdapter adapter;

    public LikedRecipesFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {
        apiCategory.getCategories();



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCategoriesRecivedEvent(final category[] categoreis) {
        if (categoreis !=null && categoreis.length != 0){

            CategoriesAdapter adapter = new CategoriesAdapter(getContext(), categoreis);
            categoriesList.setAdapter(adapter);
            categoriesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    showItemList(categoreis[position].getCatId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }else{

        }
    };

    @Click
    void showCategories(){
        showCategories.setVisibility(View.INVISIBLE);
        categoriesList.setVisibility(View.VISIBLE);
    }

    void showItemList(String categoryId){

    }



    private void fetch() {

        recyclerViewLikedRecipes.setAdapter(adapter);
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
