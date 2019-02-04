package info.sayederfanarefin.meisterkoch.ui.authentication;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreFragment;

/**
 * Created by Sayed Erfan Arefin on 10/5/18.
 */

@EFragment(R.layout.content_terms_and_condition)
public class DisclaimerTermsAndConditionFragment extends CoreFragment {

    @ViewById
    TextView textViewTc;

    public DisclaimerTermsAndConditionFragment() {
        //Mandatory default constructor
    }

    @AfterViews
    void afterViews() {

        textViewTc.setText(Html.fromHtml(getString(R.string.terms_condition)));
        textViewTc.setClickable(true);
        textViewTc.setMovementMethod(LinkMovementMethod.getInstance());

    }


}
