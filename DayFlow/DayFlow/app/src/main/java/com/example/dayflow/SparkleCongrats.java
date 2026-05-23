package com.example.dayflow;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SparkleCongrats {

    public static void show(Activity activity, String message) {
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 180);
        toast.show();

        showSparkles(activity);
    }

    private static void showSparkles(Activity activity) {
        ViewGroup root = (ViewGroup) activity.getWindow().getDecorView();
        Random r = new Random();

        // multiple sparkles
        for (int i = 0; i < 20; i++) {
            TextView tv = new TextView(activity);
            tv.setText("✨");
            tv.setTextSize(20 + r.nextInt(14));
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            int width = Math.max(root.getWidth(), 1);
            int height = Math.max(root.getHeight(), 1);

            int startX = 40 + r.nextInt(Math.max(width - 80, 1));
            int startY = height - 260 - r.nextInt(140);

            tv.setX(startX);
            tv.setY(startY);
            tv.setAlpha(0f);

            root.addView(tv);

            tv.animate()
                    .alpha(1f)
                    .translationYBy(-250 - r.nextInt(250))
                    .setDuration(900 + r.nextInt(500))
                    .withEndAction(() -> root.removeView(tv))
                    .start();
        }
    }
}
