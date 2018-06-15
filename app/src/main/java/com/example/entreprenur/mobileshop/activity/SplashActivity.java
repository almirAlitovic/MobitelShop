package com.example.entreprenur.mobileshop.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.utils.SharedPreferencesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_splash_activty)
public class SplashActivity extends AppCompatActivity {

    @ViewById(R.id.android_image)
    protected ImageView androidImage;
    @ViewById(R.id.ios_image)
    protected ImageView iosImage;
    @ViewById(R.id.windows_image)
    protected ImageView windowsImage;

    private static final int ANIMATION_DURATION = 300;
    private SharedPreferencesUtil sharedPreferencesUtil;


    @AfterViews
    public void init() {
        sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());

        animate();
    }


    @UiThread(delay = 300)
    public void animate() {
        if (SplashActivity.this.isFinishing()) {
            return;
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int firstPosition = -displayMetrics.widthPixels / 2;
        androidImage.setTranslationX(firstPosition);
        iosImage.setTranslationX(firstPosition);
        windowsImage.setTranslationX(firstPosition);
        int andX = (displayMetrics.widthPixels / 2 - (androidImage.getMeasuredWidth() /2));
        int iosX = (displayMetrics.widthPixels / 2 - (iosImage.getMeasuredWidth() /2));
        int winX = (displayMetrics.widthPixels / 2 - (windowsImage.getMeasuredWidth() /2));


        ObjectAnimator androidAnimator = ObjectAnimator
                .ofFloat(androidImage, "translationX", firstPosition, andX)
                .setDuration(ANIMATION_DURATION);

        ObjectAnimator iosAnimator = ObjectAnimator
                .ofFloat(iosImage, "translationX", firstPosition, iosX)
                .setDuration(ANIMATION_DURATION);

        ObjectAnimator winAnimator = ObjectAnimator
                .ofFloat(windowsImage, "translationX", firstPosition, winX)
                .setDuration(ANIMATION_DURATION);

        int newAndroidPositionX = iosX - (displayMetrics.widthPixels / 2 - (androidImage.getMeasuredWidth() /2));
        int newWinPositionX = iosX + (displayMetrics.widthPixels / 2 - (windowsImage.getMeasuredWidth() /2));

        int newYPosition = (int) iosImage.getY();

        final ObjectAnimator newAndroidXAnimator = ObjectAnimator
                .ofFloat(androidImage, "translationX", andX, newAndroidPositionX)
                .setDuration(ANIMATION_DURATION);

        final ObjectAnimator newWinXAnimator = ObjectAnimator
                .ofFloat(windowsImage, "translationX", winX, newWinPositionX)
                .setDuration(ANIMATION_DURATION);


        final ObjectAnimator newAndroidYAnimator = ObjectAnimator
                .ofFloat(androidImage, "translationY", androidImage.getTranslationY(), newYPosition)
                .setDuration(ANIMATION_DURATION);

        final ObjectAnimator newWinYAnimator = ObjectAnimator
                .ofFloat(windowsImage, "translationY", windowsImage.getTranslationY(), -newYPosition)
                .setDuration(ANIMATION_DURATION);


        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(androidAnimator, iosAnimator, winAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (SplashActivity.this.isFinishing()) {
                    return;
                }

                AnimatorSet animatorSetTwo = new AnimatorSet();
                animatorSetTwo.playTogether(newAndroidXAnimator, newAndroidYAnimator);
                animatorSetTwo.playTogether(newWinXAnimator, newWinYAnimator);
                animatorSetTwo.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (SplashActivity.this.isFinishing()) {
                            return;
                        }

                        String userId = sharedPreferencesUtil.getUserID();
                        if (userId.isEmpty()) {
                            LoginActivity_.IntentBuilder_ builder = LoginActivity_.intent(SplashActivity.this);
                            builder.start();
                        } else {
                            MainActivity_.IntentBuilder_ builder = MainActivity_.intent(SplashActivity.this);
                            builder.start();
                        }
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animatorSetTwo.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();

    }
}
