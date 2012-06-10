package com.advancementbureau.edb;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EDBReadWriteActivity extends SuperEDBActivity {
	String fileName;
	File currentFile;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_write);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        if (mGameSettings.contains(NEW_FILE_NAME)) {
			fileName = mGameSettings.getString(NEW_FILE_NAME, "");
		}
        currentFile = new File(fileName);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
	        actionBar2.setTitle(fileName);
        }
        final Button enterButton = (Button) findViewById(R.id.EnterText);
        enterButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		String insertString = null;
        		EditText addText = (EditText) findViewById(R.id.AddText);
    			try {
					FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND|MODE_PRIVATE);
    				//FileWriter out = new FileWriter(fileName, true);
    				//BufferedWriter writer = new BufferedWriter(out);
					insertString = addText.getText().toString() + "/n";
					fos.write(insertString.getBytes());
					//writer.newLine();
			    	//writer.close();
			    	fos.close();
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
        try {
        	InputStream iFile = new FileInputStream(currentFile);
        	String strFile = inputStreamToString(iFile);
        	readData.setText(strFile);
        } catch (Exception e) {
        }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mGameSettings.contains(NEW_FILE_NAME)) {
			fileName = mGameSettings.getString(NEW_FILE_NAME, "");
		}
	}
	
	public String inputStreamToString(InputStream is) throws IOException {
    	StringBuffer sBuffer = new StringBuffer();
    	DataInputStream dataIO = new DataInputStream(is);
    	String strLine = null;
    	while ((strLine = dataIO.readLine()) != null) {
    		sBuffer.append(strLine + "\n");
    	}
    	dataIO.close();
    	is.close();
    	return sBuffer.toString();
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
