package com.delta.Final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class ChoosePlayersDatabase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_OVR = "overall";
	public static final String KEY_PRICE = "price";
	public static final String KEY_POSITION = "position";

	private static final String DATABASE_NAME = "ChoosePlayersdb";
	private static final String DATABASE_TABLE = "Players";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME
			+ " TEXT NOT NULL, " + KEY_OVR +" INTEGER NOT NULL, " + KEY_PRICE +" INTEGER NOT NULL);";
	
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
	public ChoosePlayersDatabase(Context context){
		c=context;
		
	}
	public ChoosePlayersDatabase open() throws SQLException{
		cc=new ContactClass(c);
		db=cc.getWritableDatabase();
		return this;
		
	}
	
	public ChoosePlayersDatabase close(){
		cc.close();
		return this;
	}
	
	public long insertData(String pos, String name, int ovr,int price) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(KEY_POSITION, pos);
		cv.put(KEY_NAME, name);
		cv.put(KEY_OVR, ovr);
		cv.put(KEY_PRICE, price);
		return db.insert(DATABASE_TABLE, null, cv);
	}
	public String getData() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID,KEY_POSITION,KEY_NAME,KEY_OVR,KEY_PRICE };
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";
		
		int iPos = c.getColumnIndex(KEY_POSITION);
		int iName = c.getColumnIndex(KEY_NAME);
		int iOvr=c.getColumnIndex(KEY_OVR);
		int iPrice=c.getColumnIndex(KEY_PRICE);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iPos)
					+ "\t\t" + c.getString(iName)+ "\t\t" + c.getString(iOvr) + "\t\t" + c.getString(iPrice) + ";";
		}
		c.close();
		return result;
	}
	public long delete(String name){
		return db.delete(DATABASE_TABLE, KEY_NAME+"='"+name+"'", null);
	}
	
	public long deletetAll() {
		// TODO Auto-generated method stub
		return db.delete(DATABASE_TABLE, null, null);
	}
	
}
