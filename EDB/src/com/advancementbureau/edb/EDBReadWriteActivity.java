package com.advancementbureau.edb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EDBReadWriteActivity extends SuperEDBActivity {
	String fileName;
	File currentFile;
	int offset;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_write);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        if (mGameSettings.contains(NEW_FILE_NAME)) {
			fileName = mGameSettings.getString(NEW_FILE_NAME, "");
		}
        currentFile = new File("/data/data/com.advancementbureau.edb/files/" + fileName);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
	        actionBar2.setTitle(fileName.substring(0,fileName.length()-4));
        }
        offset = offsetIdentifier(fileName);
        updateReadData();
        
        final Button enterButton = (Button) findViewById(R.id.EnterText);
        enterButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		String insertString = null;
        		EditText addText = (EditText) findViewById(R.id.AddText);
    			try {
    				insertString = addText.getText().toString();
    				FileWriter out = new FileWriter(currentFile, true);
    				BufferedWriter writer = new BufferedWriter(out);
    				char[] inLinePieces = insertString.toCharArray();
    				char[] outLinePieces = new char[inLinePieces.length];
    				for (int i = 0; i < inLinePieces.length; i++) {
    					char outChar;
    					char inChar = inLinePieces[i];
						outChar = (char) (inChar + offset);
    					outLinePieces[i] = outChar;
    				}
    				String outLine = new String(outLinePieces);
    				writer.write(outLine);
    				writer.newLine();
    				writer.close();
    				out.close();
    				addText.setText("");
    		        updateReadData();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        });
    }
	
	public void updateReadData() {
		TextView readData = (TextView) findViewById(R.id.TextView_ReadData);
		StringBuilder text = new StringBuilder();

		try {
		    BufferedReader br = new BufferedReader(new FileReader(currentFile));
		    String line;

		    while ((line = br.readLine()) != null) {
		    	char[] inLinePieces = line.toCharArray();
				char[] outLinePieces = new char[inLinePieces.length];
				for (int i = 0; i < inLinePieces.length; i++) {
					char outChar;
					char inChar = inLinePieces[i];
					outChar = (char) (inChar - offset);
					outLinePieces[i] = outChar;
				}
				String outLine = new String(outLinePieces);
		        text.append(outLine);
		        text.append('\n');
		    }
		}
		catch (IOException e) {
		}
		readData.setText(text);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mGameSettings.contains(NEW_FILE_NAME)) {
			fileName = mGameSettings.getString(NEW_FILE_NAME, "");
		}
	}
	
	public void confirmDialog() {
		new AlertDialog.Builder(this)
        .setTitle("Confirm")
        .setIcon(R.drawable.delete_dark)
        .setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
        })
        .setPositiveButton("Delete", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				boolean success = currentFile.delete();
				deleteReportToast(success);
				if (success) {
					startActivity(new Intent(EDBReadWriteActivity.this, EDBActivity.class));
					EDBReadWriteActivity.this.finish();
				}
			}
        }).show();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.readwriteoptions, menu);
    	menu.findItem(R.id.delete_menu_item);
    	return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == android.R.id.home) {
			Intent intent2 = new Intent(this, EDBActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2); }
    	if (item.getItemId() == R.id.delete_menu_item) {
			confirmDialog(); }
    	return true;
    }
	
	private void deleteReportToast(boolean i) {
		if (i) {
			Toast.makeText(this, "Success", 1000).show();
		} else {
			Toast.makeText(this, "File NOT deleted", 1000).show();
		}
	}
}
