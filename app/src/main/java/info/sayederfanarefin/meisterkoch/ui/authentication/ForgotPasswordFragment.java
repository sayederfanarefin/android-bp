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
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.commons.Commons;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_authentication_signin_using_email_forgot_password)
public class ForgotPasswordFragment extends CoreFragment {

    @ViewById
    EditText editTextForgotPasswordEmail;

    @ViewById
    AVLoadingIndicatorView avi;

    private boolean disableButton = false;
    public ForgotPasswordFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {


    }

    @Click
    void buttonSendEmail() {

        boolean flag = false;


        if (TextUtils.isEmpty(editTextForgotPasswordEmail.getText().toString())) {
            editTextForgotPasswordEmail.setError("Enter email address!");
            flag = false;
        } else {

            if (!Commons.validateEmail(editTextForgotPasswordEmail.getText().toString())) {
                editTextForgotPasswordEmail.setError("Enter valid email address!");
                flag = false;
            } else {
                editTextForgotPasswordEmail.setError(null);
                flag = true;
                sendPasswordReset(editTextForgotPasswordEmail.getText().toString());
            }
        }

        if (flag){

            if(disableButton){
                showSnachBar("Please wait...");
            }else{
                disableButton = true;

            }

        }else{
            showSnachBar("Check input fields");
        }

    }

    @Click
    void imageViewBackButton() {
        goBack();
    }

    private void goBack(){
        getFragmentManager().popBackStack();
    }

     private void sendPasswordReset(String email){
         startAnim();
         FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()) {
                             showSnachBar("Email sent.");
                             stopAnim();
                             goBack();
                         }
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
}
