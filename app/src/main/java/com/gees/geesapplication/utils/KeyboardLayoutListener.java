package com.gees.geesapplication.utils;

import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by ServerToko on 16/09/2017.
 */

public class KeyboardLayoutListener extends AppCompatActivity {
    private boolean keyboardListenersAttached = false;
    private View rootLayout;

    AppBarLayout appBarLayout;
    RelativeLayout relativeLayout;

    public KeyboardLayoutListener(View rootLayout, AppBarLayout appBarLayout) {
        this.appBarLayout = appBarLayout;
        this.rootLayout = rootLayout;
    }

    public KeyboardLayoutListener(RelativeLayout relativeLayout){
        this.relativeLayout = relativeLayout;
    }

    public void attachKeyboardListeners(){
        if (keyboardListenersAttached) {
            return;
        }
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        keyboardListenersAttached = true;
    }

    /*public void setAppBarLayout(int id){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) appBarLayout.getLayoutParams();

        if(id==1){
            params.height = 60;
            appBarLayout.setLayoutParams(params);
        }else if(id==2){
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            appBarLayout.setLayoutParams(params);
        }
    }*/

    public void setRelativeLayout(int id){
        if(id == 1){
            relativeLayout.setGravity(Gravity.START);
        }else if(id == 2){
            relativeLayout.setGravity(Gravity.CENTER);
        }

    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect measureRect = new Rect();
            relativeLayout.getWindowVisibleDisplayFrame(measureRect);

            int keypadHeight = relativeLayout.getRootView().getHeight() - measureRect.bottom;

            if (keypadHeight > 0) {
                setRelativeLayout(1);
            } else {
                setRelativeLayout(2);
            }
        }
    };

    public void removeLayoutListener(){
        if (Build.VERSION.SDK_INT > 16) {
            relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        } else {
            relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }
}
