package info.sayederfanarefin.meisterkoch.ui.authentication;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.commons.Commons;
import info.sayederfanarefin.meisterkoch.commons.Constants;
import info.sayederfanarefin.meisterkoch.commons.SharedPrefs;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;
import info.sayederfanarefin.meisterkoch.model.users;
import info.sayederfanarefin.meisterkoch.Api.ApiLogin;
import info.sayederfanarefin.meisterkoch.ui.FirstActivity_;
//import info.sayederfanarefin.meisterkoch.ui.FirstActivity_;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_authentication_signup_using_email)
public class SignUpEmailFragment extends CoreFragment {

    @ViewById
    EditText editTextConfirmPassword;

    @ViewById
    EditText editTextPassword;

    @ViewById
    EditText editTextEmail;

    @ViewById
    EditText editTextname;

    @ViewById
    AVLoadingIndicatorView avi;

    @Bean
    ApiLogin apiLogin;

    @Bean
    SharedPrefs sharedPrefs;


//    @ViewById
//    CheckBox checkBoxAgreement;
//
//    @ViewById
//    TextView textViewAgreement;

    private FirebaseAuth auth;
    private boolean disableButton = false;


    public SignUpEmailFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {
        auth = FirebaseAuth.getInstance();

//        checkBoxAgreement.setText("");
//        textViewAgreement.setText(Html.fromHtml("I agree to the " +
//                "<a href='info.sayederfanarefin.chat.ui.authentication.DisclaimerPrivacyPloicyAndTermsConditionActivity://Kode'>TERMS AND CONDITIONS</a>"));
//        textViewAgreement.setClickable(true);
//        textViewAgreement.setMovementMethod(LinkMovementMethod.getInstance());


//        checkBoxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    checkBoxAgreement.setTextColor(getActivity().getColor(R.color.colorPrimary));
//                }else{
//                    checkBoxAgreement.setTextColor(getActivity().getColor(R.color.colorGray));
//                }
//            }
//        });

    }


    @Click
    void buttonSignup() {
//        if (checkBoxAgreement.isChecked()) {

        boolean flag = false;


        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Enter email address!");
            flag = false;
        } else {

            if (!Commons.validateEmail(editTextEmail.getText().toString())) {
                editTextEmail.setError("Enter valid email address!");
                flag = false;
            } else {
                editTextEmail.setError(null);
                flag = true;
            }
        }
        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Please enter password");
            flag = false;
        } else {
            if (Commons.passwordStrength(editTextPassword.getText().toString()) == 0) {
                editTextPassword.setError("Password length must be atleast 8");
                flag = false;
            } else {
                if (Commons.passwordStrength(editTextPassword.getText().toString()) == 1) {
                    editTextPassword.setError("Password should contain letters and numbers");
                    flag = false;
                } else {
                    editTextPassword.setError(null);
                    flag = true;

                }
            }
        }


        if (TextUtils.isEmpty(editTextConfirmPassword.getText().toString())) {
            editTextConfirmPassword.setError("Enter confirm password!");
        } else {
            if (!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())) {
                editTextConfirmPassword.setError("Passwords do not match");
                flag = false;
            } else {
                editTextConfirmPassword.setError(null);
                flag = true;
            }
        }

        if (TextUtils.isEmpty(editTextname.getText().toString())) {
            editTextname.setError("Enter Name!");
        } else {

            editTextConfirmPassword.setError(null);
            flag = true;

        }


        if (flag) {
            //sign up user
            if (disableButton) {
                showSnachBar("Please wait. Signning up...");
            } else {

                firebaseSignUp(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }

        } else {
            showSnachBar("Sign up failed");
        }
//        } else {
//            showSnachBar("You have to agree to Terms and Conditions");
//        }

        //((AuthenticationActivity_)getActivity()).loadFragment(SignInEmailFragment_.builder().build());
    }


    private void firebaseSignUp(String email, String password) {
        disableButton = true;
        startAnim();
        apiLogin.signup(editTextname.getText().toString(), email,password);
    }

    @Click
    void imageViewBackButton() {
        getFragmentManager().popBackStack();
    }

    private void saveUserInfo(final users user) {

        Map<String, String> userInfo = new HashMap<String, String>();
        userInfo.put(Constants.dbUserUserName, user.getUsername());
        userInfo.put(Constants.dbUserUserEmail, user.getEmail());



        //usersRef.child(Constants.dbUserUserName).setValue(createProfileName.getText().toString());
        usersRef = rootRef.child(Constants.dbUserLocation+ "/" + user.getUid());
        usersRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                sharedPrefs.saveUserInSharedPref(user);
                stopAnim();
                showSnachBar("Email sent! Please verify your email address.");
                FirstActivity_.intent(getContext()).start();
            }
        });
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
    public void onSignupEvent(users user) {
        if (user !=null){
            sharedPrefs.defaultSettings();
            saveUserInfo(user);

        }else{
            showSnachBar("Sign up failed, please try again later.");
            stopAnim();
        }

        disableButton = false;
    };
}
