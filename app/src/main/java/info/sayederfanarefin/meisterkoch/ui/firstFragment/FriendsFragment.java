package info.sayederfanarefin.meisterkoch.ui.firstFragment;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_friends)
public class FriendsFragment extends CoreFragment {

    @ViewById
    Button buttonAddFriendEmptyView;

    @ViewById
    RelativeLayout relativeLayoutEmptyViewFriendsList;

    @ViewById
    LinearLayout linearLayoutFloartingAddFriends;

    @ViewById
    RecyclerView friendsRecyclerView;

    @ViewById
    RelativeLayout RealtiveLayoutNotEmptyFriendsList;


    public FriendsFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {

    }

}
