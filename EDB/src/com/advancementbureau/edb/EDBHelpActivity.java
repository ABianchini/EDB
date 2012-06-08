package com.advancementbureau.edb;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

public class EDBHelpActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
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