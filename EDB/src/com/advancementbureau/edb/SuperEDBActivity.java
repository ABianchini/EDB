package com.advancementbureau.edb;

import android.app.Activity;
import android.content.SharedPreferences;

public class SuperEDBActivity extends Activity {
	
	SharedPreferences mGameSettings;
	
	public static final String GAME_PREFERENCES = "GamePrefs";
	
	//if true the first boot has not yet happened
	public static final String FIRST_BOOT = "boot";
	public static final String NEW_FILE_NAME = "newFile";
	public static final String FILE_CHOICE = "file";
	
}