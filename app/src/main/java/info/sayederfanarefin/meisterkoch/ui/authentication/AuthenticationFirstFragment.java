package info.sayederfanarefin.meisterkoch.ui.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.event.MessageEvent;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_authentication_first_screen)
public class AuthenticationFirstFragment extends CoreFragment {

    @ViewById
    LoginButton fbSignIn;

    private FirebaseAuth auth;

    CallbackManager mCallbackManager;

    public AuthenticationFirstFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        fbSignIn.setReadPermissions("email", "public_profile");
        fbSignIn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                LOG("Cancled");
            }

            @Override
            public void onError(FacebookException error) {
                LOG(error.getMessage());
            }
        });

    }


    @Click
    void buttonSignIn(){
        ((AuthenticationActivity_)getActivity()).loadChildFragment(SignInEmailFragment_.builder().build());
    }
    @Click
    void buttonSignUp(){
        ((AuthenticationActivity_)getActivity()).loadChildFragment(SignUpEmailFragment_.builder().build());
    }

    @Click
    void buttonFbSignIn(){
        fbSignIn.performClick();
    }


    private void handleFacebookAccessToken(AccessToken token) {
        LOG("handleFacebookAccessToken");
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = auth.getCurrentUser();
                            LOG("facebook login success");

                        } else {
                            // If sign in fails, display a message to the user.
                            LOG("facebook login failed");

                        }

                        // ...
                    }
                });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        showSnachBar("activity result ");
//        // Pass the activity result back to the Facebook SDK
//
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        mCallbackManager.onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    };

}
