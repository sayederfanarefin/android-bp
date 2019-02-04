package info.sayederfanarefin.meisterkoch.ui.authentication;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreActivity;

/**
 * Created by erfan on 10/5/18.
 */

@EActivity(R.layout.content_terms_and_condition_privacy_and_policy)
public class DisclaimerPrivacyPloicyAndTermsConditionActivity extends CoreActivity {

    @ViewById
    LinearLayout buttonPrivacyAndPolicy;

    @ViewById
    LinearLayout buttonTermsAndCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);


    }

    @AfterViews
    void afterViews() {
        loadTermsAndCondition();
    }


    @Click
    void buttonTermsAndCondition(){
        loadTermsAndCondition();
    }

    @Click
    void buttonPrivacyAndPolicy(){
        locadPrivacyAndCondition();
    }

    private void loadTermsAndCondition(){
        getSupportActionBar().setTitle("Terms and Condition");
        loadFragment(DisclaimerTermsAndConditionFragment_.builder().build());
        buttonPrivacyAndPolicy.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        buttonTermsAndCondition.setBackgroundColor(getResources().getColor(R.color.colorAccentPrimary));
    }

    private void locadPrivacyAndCondition(){
        getSupportActionBar().setTitle("Privacy and Policy");
        loadFragment(DisclaimerPrivacyAndPolicyFragment_.builder().build());
        buttonTermsAndCondition.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        buttonPrivacyAndPolicy.setBackgroundColor(getResources().getColor(R.color.colorAccentPrimary));
    }
}
