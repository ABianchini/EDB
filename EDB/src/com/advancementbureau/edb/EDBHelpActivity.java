package com.advancementbureau.edb;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class EDBHelpActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
        }
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        
        intent = new Intent().setClass(this, EDBWriteActivity.class);
        spec = tabHost.newTabSpec("write").setIndicator("Write").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, EDBReadActivity.class);
        spec = tabHost.newTabSpec("read").setIndicator("Read").setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, EDBAboutActivity.class);
        spec = tabHost.newTabSpec("about").setIndicator("About").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
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