package com.advancementbureau.edb;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EDBSettingsActivity extends SuperEDBActivity {
	boolean passwordSet;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        if (mGameSettings.contains(PASSWORD_SET)) {
			passwordSet = mGameSettings.getBoolean(PASSWORD_SET, false);
		}
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
        }
        if (passwordSet) {
			setContentView(R.layout.settings_pass);
			LinearLayout removePasswordButton = (LinearLayout) findViewById(R.id.RemovePasswordButton);
			removePasswordButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
        		mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
				Editor editor = mGameSettings.edit();
		    	editor.putBoolean(PASSWORD_SET, false);
		    	editor.commit();
		    	startActivity(new Intent(EDBSettingsActivity.this, EDBSettingsActivity.class));
        	}
			});
		} else {
			setContentView(R.layout.settings);
		}
        
        LinearLayout setPasswordButton = (LinearLayout) findViewById(R.id.PasswordButton);
        setPasswordButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		passwordDialog();
        	}
        });
        
    }
	
	public void passwordDialog() {
		Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.password_dialog, (ViewGroup) findViewById(R.id.layout_root3));
    	
    	final EditText passOne = (EditText) layout.findViewById(R.id.PasswordOne_EditText);
    	final EditText passTwo = (EditText) layout.findViewById(R.id.PasswordTwo_EditText);
		
		new AlertDialog.Builder(this)
        .setTitle(R.string.new_file)
        .setIcon(R.drawable.pass)
        .setView(layout)
        .setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
        })
        .setPositiveButton("Save", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				String passwordStringOne = passOne.getText().toString();
				String passwordStringTwo = passTwo.getText().toString();
				if(!passwordStringOne.equals(passwordStringTwo)) {
					toastNoMatch();
					passwordDialog();
				} else {
					mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
					Editor editor = mGameSettings.edit();
			    	editor.putString(PASSWORD, passwordStringOne);
			    	editor.putBoolean(PASSWORD_SET, true);
			    	editor.commit();
			    	startActivity(new Intent(EDBSettingsActivity.this, EDBSettingsActivity.class));
				}
			}
        }).show();
	}
	
	private void toastNoMatch() {
		Toast.makeText(this, "Passwords did not match", 1000).show();
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
