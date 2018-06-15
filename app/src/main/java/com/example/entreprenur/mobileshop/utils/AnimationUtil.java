package com.example.entreprenur.mobileshop.utils;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimationUtil {

    public static void animateRecyclerViewScrolling(RecyclerView.ViewHolder viewHolder,
                                                    boolean scrollingDown) {
        applySlidingAnimationToRecyclerView(viewHolder, scrollingDown);
    }

    private static void applySlidingAnimationToRecyclerView(RecyclerView.ViewHolder viewHolder,
                                                            boolean scrollingDown) {


        ObjectAnimator animatorTranslateY = new ObjectAnimator();

        int adapterPosition = viewHolder.getAdapterPosition();

        if (adapterPosition < 0) {
            return;
        }

        switch (viewHolder.getAdapterPosition() % 3) {
            case 0:
                animatorTranslateY = ObjectAnimator.ofFloat(viewHolder.itemView, "translationY",
                        scrollingDown ? 400 : -400, 0);
                break;
            case 1:
                animatorTranslateY = ObjectAnimator.ofFloat(viewHolder.itemView, "translationY",
                        scrollingDown ? 340 : -340, 0);
                break;
            case 2:
                animatorTranslateY = ObjectAnimator.ofFloat(viewHolder.itemView, "translationY",
                        scrollingDown ? 460 : -460, 0);
                break;
            default:
                break;
        }

        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator =
                new AccelerateDecelerateInterpolator();
        animatorTranslateY.setDuration(200);
        animatorTranslateY.setInterpolator(accelerateDecelerateInterpolator);
        animatorTranslateY.start();
    }
}
