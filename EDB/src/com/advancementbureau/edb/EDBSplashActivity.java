package com.advancementbureau.edb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class EDBSplashActivity extends SuperEDBActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	Animate();
        } else {
        	startActivity(new Intent(EDBSplashActivity.this, EDBActivity.class));
    		EDBSplashActivity.this.finish();
        }
    }
    
    private void Animate() {
    	ImageView logo = (ImageView) findViewById(R.id.ImageView_EDBLogo);
    	Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.flicker);
    	logo.startAnimation(fade1);
    	fade1.setAnimationListener(new AnimationListener() {
        	public void onAnimationEnd(Animation animation) {
        		try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		startActivity(new Intent(EDBSplashActivity.this, EDBActivity.class));
        		EDBSplashActivity.this.finish();
        	}
        	public void onAnimationRepeat(Animation animation) {
        	}
        	public void onAnimationStart(Animation animation) {
        	}
        });
    }
}