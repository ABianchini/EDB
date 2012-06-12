package com.advancementbureau.edb;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EDBActivity extends SuperEDBActivity {
	boolean firstBootDone;
	boolean authorized;
	boolean passSet;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", firstBootDone);
            editor.commit();
            firstDialog();
        }
    }
    
    public void authDialog(final String s) {
    	Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.auth_dialog, (ViewGroup) findViewById(R.id.layout_root4));
    	
    	final EditText passEntry = (EditText) layout.findViewById(R.id.PasswordAuth_EditText);
		
		new AlertDialog.Builder(this)
        .setTitle(R.string.password_title)
        .setIcon(R.drawable.pass)
        .setView(layout)
        .setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
        })
        .setPositiveButton("Authorize", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				String password = "";
				mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
		        if (mGameSettings.contains(PASSWORD)) {
					password = mGameSettings.getString(PASSWORD, "");
				}
		        authorized = password.equals(passEntry.getText().toString());
		        if (authorized) {
		        	Editor editor = mGameSettings.edit();
			    	editor.putString(NEW_FILE_NAME, s);
			    	editor.commit();
			    	startActivity(new Intent(EDBActivity.this, EDBReadWriteActivity.class));
		        } else {
		        	toastNoAuth();
		        }
			}
        }).show();
    }
    
    public void authOptionsDialog(final String s) {
    	Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.auth_dialog, (ViewGroup) findViewById(R.id.layout_root4));
    	
    	final EditText passEntry = (EditText) layout.findViewById(R.id.PasswordAuth_EditText);
		
		new AlertDialog.Builder(this)
        .setTitle(R.string.password_title)
        .setIcon(R.drawable.pass)
        .setView(layout)
        .setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
        })
        .setPositiveButton("Authorize", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				String password = "";
				mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
		        if (mGameSettings.contains(PASSWORD)) {
					password = mGameSettings.getString(PASSWORD, "");
				}
		        authorized = password.equals(passEntry.getText().toString());
		        if (authorized) {
		        	fileOptionsDialog(s);
		        } else {
		        	toastNoAuth();
		        }
			}
        }).show();
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
				if(fileName != ".txt") {
					Editor editor = mGameSettings.edit();
			    	editor.putString(NEW_FILE_NAME, fileName);
			    	editor.commit();
					try {
						FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND|MODE_PRIVATE);
						String outLine = "";
						fos.write(outLine.getBytes());
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			    	startActivity(new Intent(EDBActivity.this, EDBReadWriteActivity.class));
				} else {
					toastName();
				}
			}
        }).show();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	ListView menuList = (ListView) findViewById(R.id.FilesListView);
        File dir = new File("/data/data/com.advancementbureau.edb/files");
		final String[] files = dir.list();
		String[] filesNoTxt = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			filesNoTxt[i] = files[i].substring(0, files[i].length()-4);
		}
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,R.layout.menu_item, filesNoTxt);
        menuList.setAdapter(adapt);
        
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
        		mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
		        if (mGameSettings.contains(PASSWORD_SET)) {
					passSet = mGameSettings.getBoolean(PASSWORD_SET, false);
				}
		        if (passSet) {
	        		authDialog(files[position]);
		        } else {
		        	Editor editor = mGameSettings.edit();
			    	editor.putString(NEW_FILE_NAME, files[position]);
			    	editor.commit();
			    	startActivity(new Intent(EDBActivity.this, EDBReadWriteActivity.class));
		        }
        	}
        });
        menuList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
            	mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
		        if (mGameSettings.contains(PASSWORD_SET)) {
					passSet = mGameSettings.getBoolean(PASSWORD_SET, false);
				}
		        if (passSet) {
	        		authOptionsDialog(files[pos]);
		        } else {
		        	fileOptionsDialog(files[pos]);
		        }
            	return true;
            }
        });

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.mainoptions, menu);
    	menu.findItem(R.id.add_menu_item);
    	menu.findItem(R.id.settings_menu_item).setIntent(new Intent(this, EDBSettingsActivity.class));
    	menu.findItem(R.id.changelog_menu_item).setIntent(new Intent(this, EDBChangelogActivity.class));
    	menu.findItem(R.id.help_menu_item).setIntent(new Intent(this, EDBHelpActivity.class));
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == R.id.changelog_menu_item) {
    		startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.settings_menu_item) {
    		startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.help_menu_item) {
    		startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.add_menu_item) {
    		newFileDialog(); }
    	return true;
    }
    
    private void fileOptionsDialog(final String s) {
    	final CharSequence[] items = {"Delete", "Export"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Options");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        if (item == 0) confirmDialog(s);
    	        if (item == 1) exportFile(s);
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    public void exportFile(String s) {
    	String fileName = s;
    	int offset = offsetIdentifier(fileName);
        File folder = new File(Environment.getExternalStorageDirectory() + "/EDB");
        if (!folder.exists()) {
        	boolean success = folder.mkdir();
        }
		try {
			String strLine;
			InputStream is = openFileInput(fileName);
	    	DataInputStream dataIO = new DataInputStream(is);
	    	FileWriter out = new FileWriter(Environment.getExternalStorageDirectory() + "/EDB/" + fileName, true);
			BufferedWriter writer = new BufferedWriter(out);
			while ((strLine = dataIO.readLine()) != null) {
				char[] inLinePieces = strLine.toCharArray();
				char[] outLinePieces = new char[inLinePieces.length];
				for (int i = 0; i < inLinePieces.length; i++) {
					char outChar;
					char inChar = inLinePieces[i];
					outChar = (char) (inChar - offset);
					outLinePieces[i] = outChar;
				}
				String outLine = new String(outLinePieces);
				writer.append(outLine);
				writer.newLine();
	    	}
			writer.close();
			out.close();
			dataIO.close();
	    	is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Toast.makeText(getBaseContext(), fileName + " has been written to /EDB/", 1000).show();
	}
    
    public void confirmDialog(String s) {
    	String fileName = s;
    	final File currentFile = new File("/data/data/com.advancementbureau.edb/files/" + fileName);
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
					startActivity(new Intent(EDBActivity.this, EDBActivity.class));
					EDBActivity.this.finish();
				}
			}
        }).show();
	}
    
    private void toastNoAuth() {
    	Toast.makeText(this, "Access Denied", 1000).show();
    }
    private void toastName() {
    	Toast.makeText(this, "Needs a name", 1500).show();
    }
    private void deleteReportToast(boolean i) {
		if (i) {
			Toast.makeText(this, "Success", 1000).show();
		} else {
			Toast.makeText(this, "File NOT deleted", 1000).show();
		}
	}
}