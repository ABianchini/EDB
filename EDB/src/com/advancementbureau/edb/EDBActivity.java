package com.advancementbureau.edb;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class EDBActivity extends SuperEDBActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        
        File folder = new File(Environment.getExternalStorageDirectory() + "/EDB");
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdir();
	        if (!success) {
	        	Toast.makeText(this, "Failed", 1000).show();
	        } else {
	        	Toast.makeText(this, "Worked! :D", 1000).show();
	        }
        }
        if (mGameSettings.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", false);
            editor.commit();
            //test
        }
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.mainoptions, menu);
    	menu.findItem(R.id.changelog_menu_item).setIntent(new Intent(this, EDBChangelogActivity.class));
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	//Changelog
    	if (item.getItemId() == R.id.changelog_menu_item) {
    		startActivity(item.getIntent()); }
    	return true;
    }
}