package com.advancementbureau.edb;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class EDBReadWriteActivity extends SuperEDBActivity {
	String fileName;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_write);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        if (mGameSettings.contains(NEW_FILE_NAME)) {
			fileName = mGameSettings.getString(NEW_FILE_NAME, "");
		}
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
	        actionBar2.setTitle(fileName);
        }
        final LinearLayout enterButton = (LinearLayout) findViewById(R.id.EnterText);
        enterButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		
        	}
        });
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mGameSettings.contains(NEW_FILE_NAME)) {
			fileName = mGameSettings.getString(NEW_FILE_NAME, "");
		}
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == android.R.id.home) {
			Intent intent2 = new Intent(this, EDBActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2); }
    	return true;
    }
}
