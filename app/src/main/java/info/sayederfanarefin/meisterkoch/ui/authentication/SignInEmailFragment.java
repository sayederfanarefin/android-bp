package info.sayederfanarefin.meisterkoch.ui.authentication;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.wang.avi.AVLoadingIndicatorView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.model.users;
import info.sayederfanarefin.meisterkoch.Api.ApiLogin;
import info.sayederfanarefin.meisterkoch.ui.FirstActivity_;


/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_authentication_signin_using_email)
public class SignInEmailFragment extends CoreFragment {

    @ViewById
    EditText editTextEmail;
    @ViewById
    EditText editTextPassword;

    @ViewById
    AVLoadingIndicatorView avi;


    @Bean
    SharedPrefs sharedPrefs;

    @Bean
    ApiLogin apiLogin;

    public SignInEmailFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {

    }

    @Click
    void buttonSignin() {

        startAnim();

        boolean flag = false;

        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Enter email address!");
            flag = false;
        } else {
            editTextEmail.setError(null);
            flag = true;
        }
        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Please enter password");
            flag = false;
        } else {
            editTextPassword.setError(null);
            flag = true;
        }
        if (flag) {

             apiLogin.login(editTextEmail.getText().toString(), editTextPassword.getText().toString());

        } else {
            showSnachBar("Sign in failed");
            stopAnim();
        }

    }

    @Click
    void imageViewBackButton() {
        getFragmentManager().popBackStack();
    }

    @Click
    void forgotPassword(){
        ((AuthenticationActivity_)getActivity()).loadChildFragment(ForgotPasswordFragment_.builder().build());
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




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSigninEvent(users user) {
        if (user !=null){
            sharedPrefs.defaultSettings();
            sharedPrefs.saveUserInSharedPref(user);
            showSnachBar("Welcom back!");
            FirstActivity_.intent(getContext()).start();

        }else{
            showSnachBar("Sign in failed");
        }

        stopAnim();
    };

}
