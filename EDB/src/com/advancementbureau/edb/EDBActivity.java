package com.advancementbureau.edb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EDBActivity extends SuperEDBActivity {
	boolean firstBootDone;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        /*
        File folder = new File(Environment.getExternalStorageDirectory() + "/EDB");
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdir();
	        if (!success) {
	        	Toast.makeText(this, "Failed", 1000).show();
	        } else {
	        	Toast.makeText(this, "Worked! :D", 1000).show();
	        }
        }*/
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", firstBootDone);
            editor.commit();
            firstDialog();
        }
    }
    
    public void firstDialog() {
    	firstBootDone = false;
    	Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.first_dialog, (ViewGroup) findViewById(R.id.layout_root));
		
		new AlertDialog.Builder(this)
        .setTitle(R.string.read_cap)
        .setIcon(R.drawable.alert)
        .setView(layout)
        .setNegativeButton("No thanks", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
        })
        .setPositiveButton("Help", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				startActivity(new Intent(EDBActivity.this, EDBHelpActivity.class));
			}
        }).show();
    }
    
    public void newFileDialog() {
    	Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.new_file_dialog, (ViewGroup) findViewById(R.id.layout_root2));
    	
    	final EditText newFileName = (EditText) layout.findViewById(R.id.FileName_EditText);
		
		new AlertDialog.Builder(this)
        .setTitle(R.string.new_file)
        .setIcon(R.drawable.add_dark)
        .setView(layout)
        .setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
        })
        .setPositiveButton("Create", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				String fileName = newFileName.getText().toString() + ".txt";
				Editor editor = mGameSettings.edit();
		    	editor.putString(NEW_FILE_NAME, fileName);
		    	editor.commit();
		    	String input = "";
				try {
					FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND|MODE_PRIVATE);
					fos.write(input.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	startActivity(new Intent(EDBActivity.this, EDBReadWriteActivity.class));
			}
        }).show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.mainoptions, menu);
    	menu.findItem(R.id.add_menu_item);
    	menu.findItem(R.id.changelog_menu_item).setIntent(new Intent(this, EDBChangelogActivity.class));
    	menu.findItem(R.id.help_menu_item).setIntent(new Intent(this, EDBHelpActivity.class));
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	//Changelog
    	if (item.getItemId() == R.id.changelog_menu_item) {
    		startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.help_menu_item) {
    		startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.add_menu_item) {
    		newFileDialog(); }
    	return true;
    }
}