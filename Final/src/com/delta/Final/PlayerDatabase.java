package com.delta.Final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PlayerDatabase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_POSITION = "pos";
	public static final String KEY_NAME = "name";
	public static final String KEY_OVRLL = "overall";
	//public static final String KEY_DESC = "description";
	
	private static final String DATABASE_NAME = "Playersdb";
	private static final String DATABASE_TABLE_1 = "Arsenal";
	private static final String DATABASE_TABLE_2 = "ManUtd";
	private static final String DATABASE_TABLE_3 = "Chelsea";
	private static final String DATABASE_TABLE_4 = "Liverpool";
	private static final String DATABASE_TABLE_5 = "ManCity";
	private static final String DATABASE_TABLE_6 = "MyTeam";
	private int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE_1 = "CREATE TABLE "
			+ DATABASE_TABLE_1 + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME +" TEXT NOT NULL, "+ KEY_OVRLL + " INTEGER NOT NULL);";
	private static final String DATABASE_CREATE_2 = "CREATE TABLE "
			+ DATABASE_TABLE_2 + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME +" TEXT NOT NULL, "+ KEY_OVRLL + " INTEGER NOT NULL);";
	private static final String DATABASE_CREATE_3 = "CREATE TABLE "
			+ DATABASE_TABLE_3 + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME +" TEXT NOT NULL, "+ KEY_OVRLL + " INTEGER NOT NULL);";
	private static final String DATABASE_CREATE_4 = "CREATE TABLE "
			+ DATABASE_TABLE_4 + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME +" TEXT NOT NULL, "+ KEY_OVRLL + " INTEGER NOT NULL);";
	private static final String DATABASE_CREATE_5 = "CREATE TABLE "
			+ DATABASE_TABLE_5 + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME +" TEXT NOT NULL, "+ KEY_OVRLL + " INTEGER NOT NULL);";
	private static final String DATABASE_CREATE_6 = "CREATE TABLE "
			+ DATABASE_TABLE_6 + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POSITION
			+ " TEXT NOT NULL, " + KEY_NAME +" TEXT NOT NULL, "+ KEY_OVRLL + " INTEGER NOT NULL);";
	public String DATABASE_TABLE;
Context c;
SQLiteDatabase db;
ContactClass cc;

private class ContactClass extends SQLiteOpenHelper{

	public ContactClass(Context context) {
		super(context,DATABASE_NAME, null,DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE_1);
		db.execSQL(DATABASE_CREATE_2);
		db.execSQL(DATABASE_CREATE_3);
		db.execSQL(DATABASE_CREATE_4);
		db.execSQL(DATABASE_CREATE_5);
		db.execSQL(DATABASE_CREATE_6);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_1);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_2);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_3);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_4);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_5);
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_6);
		onCreate(db);
	}
	
}

	public PlayerDatabase(Context context){
		c=context;
		
	}
	public PlayerDatabase open() throws SQLException{
		cc=new ContactClass(c);
		db=cc.getWritableDatabase();
		return this;
		
	}
	
	public PlayerDatabase close(){
		cc.close();
		return this;
	}
	
	public long insertData(String pos, String name, int ovr) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(KEY_POSITION, pos);
		cv.put(KEY_NAME, name);
		cv.put(KEY_OVRLL, ovr);
		//cv.put(KEY_DESC, desc);
		return db.insert(DATABASE_TABLE, null, cv);
	}
	
	public String getData() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID,KEY_POSITION,KEY_NAME,KEY_OVRLL };
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,KEY_POSITION);
		String result = "";
		
		int iPos = c.getColumnIndex(KEY_POSITION);
		int iName = c.getColumnIndex(KEY_NAME);
		int iOvr=c.getColumnIndex(KEY_OVRLL);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iPos)
					+ "\t\t" + c.getString(iName)+ "\t\t\t" + c.getString(iOvr) + "\n";
		}
		c.close();
		return result;
	}
	
	public String getAllPos() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_POSITION};
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";		
		int iPos = c.getColumnIndex(KEY_POSITION);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iPos) + ";";
		}
		c.close();
		return result;
	}
	
	public String getAllName() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_NAME};
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";		
		int iName = c.getColumnIndex(KEY_NAME);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iName) + ";";
		}
		c.close();
		return result;
	}
	
	public String getAllOvr() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_OVRLL};
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";		
		int iOvr = c.getColumnIndex(KEY_OVRLL);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iOvr) + ";";
		}
		c.close();
		return result;
	}
	
	public float getAvg(){
		float result;
		String query="SELECT AVG(overall) FROM "+DATABASE_TABLE;
		Cursor c=db.rawQuery(query, null);
		c.moveToFirst();
		result=Float.parseFloat(c.getString(0));
		c.close();
		return result;
		
	}
	
	public long delete(String name){
		return db.delete(DATABASE_TABLE, KEY_NAME+"='"+name+"'", null);
	}
	
	public long deleteAll(){
		return db.delete(DATABASE_TABLE, null, null);
	}

}


