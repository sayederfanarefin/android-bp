package info.sayederfanarefin.meisterkoch.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.View;
import android.widget.ImageView;


import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import info.sayederfanarefin.meisterkoch.R;
import info.sayederfanarefin.meisterkoch.core.CoreActivity;

//import info.sayederfanarefin.chat.ui.FirstActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.content_splash_screen_animated)
@Fullscreen
public class SplashScreenActivity extends CoreActivity {

//    @ViewById
//    AnimatedSvgView animated_svg_view ; //= (AnimatedSvgView) findViewById(R.id.animated_svg_view);


//    @ViewById
//    ImageView ani;
//
   @ViewById
VectorMasterView heart_vector;

    boolean flag = true;

    @AfterViews
    void afterViews() {

        final PathModel outline = heart_vector.getPathModelByName("outline");
        final PathModel box = heart_vector.getPathModelByName("box");
        final PathModel line1 = heart_vector.getPathModelByName("line1");
        final PathModel line2 = heart_vector.getPathModelByName("line2");

        outline.setStrokeColor(getColor(R.color.colorWhite));
        outline.setTrimPathEnd(0.0f);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                // set trim end value and update view
                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heart_vector.update();
            }
        });



        box.setStrokeColor(getColor(R.color.colorWhite));
        box.setTrimPathEnd(0.0f);
        ValueAnimator valueAnimatorBox = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorBox.setDuration(1000);
        valueAnimatorBox.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                // set trim end value and update view
                box.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heart_vector.update();
            }
        });

        line1.setStrokeColor(getColor(R.color.colorWhite));
        line1.setTrimPathEnd(0.0f);
        ValueAnimator valueAnimatorLine1 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorLine1.setDuration(1000);
        valueAnimatorLine1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                // set trim end value and update view
                line1.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heart_vector.update();
            }
        });

        line2.setStrokeColor(getColor(R.color.colorWhite));
        line2.setTrimPathEnd(0.0f);
        ValueAnimator valueAnimatorLine2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorLine2.setDuration(1000);
        valueAnimatorLine2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                // set trim end value and update view
                line2.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heart_vector.update();
            }
        });

        valueAnimator.start();
        valueAnimatorBox.start();
        valueAnimatorLine2.start();
        valueAnimatorLine1.start();

        //animated_svg_view.start();
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openFirstActivity();
                finish();
            }
        }, 1000);
    }

    private void openFirstActivity() {
        FirstActivity_.intent(this).start();
    }


//    public void animate(View view) {
//        ImageView v = (ImageView) view;
//        Drawable d = v.getDrawable();
//        if (d instanceof AnimatedVectorDrawable) {
//            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
//            avd.start();
//        } else if (d instanceof AnimatedVectorDrawableCompat) {
//            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) d;
//            avd.start();
//        }
//    }
}
