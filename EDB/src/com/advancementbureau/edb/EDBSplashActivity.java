package com.advancementbureau.edb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = mGameSettings.edit();
    	
    	
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean(PASSWORD_SET, false);
        	editor.commit();
        	Animate();
        	textFileCreate();
        } else {
        	startActivity(new Intent(EDBSplashActivity.this, EDBActivity.class));
    		EDBSplashActivity.this.finish();
        }
    }
    
    private void textFileCreate() {
		String sampleFile = "Just a sample file\nFeel free to delete";
		try {
			char[] inLinePieces = sampleFile.toCharArray();
			char[] outLinePieces = new char[inLinePieces.length];
			for (int i = 0; i < inLinePieces.length; i++) {
				char outChar;
				char inChar = inLinePieces[i];
				outChar = (char) (inChar + 10);
				outLinePieces[i] = outChar;
			}
			String outLine = new String(outLinePieces);
			FileOutputStream fos = openFileOutput("Sample.txt", Context.MODE_APPEND|MODE_PRIVATE);
			fos.write(outLine.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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