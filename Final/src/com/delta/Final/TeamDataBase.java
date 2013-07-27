package com.delta.Final;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TeamDataBase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_CLUB = "club";
	public static final String KEY_OVR = "overall";
	//public static final String KEY_ = "event";
	//public static final String KEY_ = "description";

	private static final String DATABASE_NAME = "Teamsdb";
	private static final String DATABASE_TABLE = "Teams";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CLUB
			+ " TEXT NOT NULL, " + KEY_OVR +" FLOAT NOT NULL);";
	
	ContactClass cc;
	SQLiteDatabase db;
	Context c;
	private class ContactClass extends SQLiteOpenHelper{

		public ContactClass(Context context) {
			super(context, DATABASE_NAME, null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);	
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
		onCreate(db);
		}
		
	}
	
	public TeamDataBase(Context context){
		c=context;
	}
	
	public TeamDataBase open() throws SQLException{
		cc=new ContactClass(c);
		db=cc.getWritableDatabase();
		return this;
		
	}
	
	public TeamDataBase close(){
		cc.close();
		return this;
	}

	public long insertData(String team,Float ovr){
		ContentValues cv=new ContentValues();
		cv.put(KEY_CLUB, team);
		cv.put(KEY_OVR, ovr);
		return db.insert(DATABASE_TABLE, null, cv);
	}
	
}
