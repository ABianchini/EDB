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
	
	public int offsetIdentifier(String s) {
		int offset = 4;
		if (s.substring(0,1).equalsIgnoreCase("a")) offset = 3;
		if (s.substring(0,1).equalsIgnoreCase("b")) offset = 2;
		if (s.substring(0,1).equalsIgnoreCase("c")) offset = 9;
		if (s.substring(0,1).equalsIgnoreCase("d")) offset = 5;
		if (s.substring(0,1).equalsIgnoreCase("e")) offset = 4;
		if (s.substring(0,1).equalsIgnoreCase("f")) offset = 3;
		if (s.substring(0,1).equalsIgnoreCase("g")) offset = 7;
		if (s.substring(0,1).equalsIgnoreCase("h")) offset = 9;
		if (s.substring(0,1).equalsIgnoreCase("i")) offset = 11;
		if (s.substring(0,1).equalsIgnoreCase("j")) offset = 10;
		if (s.substring(0,1).equalsIgnoreCase("k")) offset = 3;
		if (s.substring(0,1).equalsIgnoreCase("l")) offset = 6;
		if (s.substring(0,1).equalsIgnoreCase("m")) offset = 4;
		if (s.substring(0,1).equalsIgnoreCase("n")) offset = 8;
		if (s.substring(0,1).equalsIgnoreCase("o")) offset = 7;
		if (s.substring(0,1).equalsIgnoreCase("p")) offset = 5;
		if (s.substring(0,1).equalsIgnoreCase("q")) offset = 2;
		if (s.substring(0,1).equalsIgnoreCase("r")) offset = 8;
		if (s.substring(0,1).equalsIgnoreCase("s")) offset = 10;
		if (s.substring(0,1).equalsIgnoreCase("t")) offset = 3;
		if (s.substring(0,1).equalsIgnoreCase("u")) offset = 11;
		if (s.substring(0,1).equalsIgnoreCase("v")) offset = 8;
		if (s.substring(0,1).equalsIgnoreCase("w")) offset = 9;
		if (s.substring(0,1).equalsIgnoreCase("x")) offset = 3;
		if (s.substring(0,1).equalsIgnoreCase("y")) offset = 6;
		if (s.substring(0,1).equalsIgnoreCase("z")) offset = 7;
		return offset;
	}
}