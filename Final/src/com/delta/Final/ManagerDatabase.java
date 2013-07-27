package com.delta.Final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class ManagerDatabase {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_BAL = "balance";
	public static final String KEY_WIN = "win";
	public static final String KEY_LOSS = "loss";
	public static final String KEY_DRAW = "draw";

	private static final String DATABASE_NAME = "Managerdb";
	private static final String DATABASE_TABLE = "Manager";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
			+ " TEXT NOT NULL, " + KEY_BAL
			+ " TEXT NOT NULL, " + KEY_WIN +" INTEGER NOT NULL, " + KEY_LOSS +" INTEGER NOT NULL, " + KEY_DRAW +" INTEGER NOT NULL);";
	
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
	public ManagerDatabase(Context context){
		c=context;
		
	}
	public ManagerDatabase open() throws SQLException{
		cc=new ContactClass(c);
		db=cc.getWritableDatabase();
		return this;
		
	}
	
	public ManagerDatabase close(){
		cc.close();
		return this;
	}
	
	public long insertData(String name, int bal,int win,int loss,int draw) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_BAL, bal);
		cv.put(KEY_WIN, win);
		cv.put(KEY_LOSS, loss);
		cv.put(KEY_DRAW, draw);
		return db.insert(DATABASE_TABLE, null, cv);
	}
	public String getData() throws SQLException{
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_NAME,KEY_BAL,KEY_WIN,KEY_LOSS,KEY_DRAW };
		Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";
		
		
		int iName = c.getColumnIndex(KEY_NAME);
		int iBal=c.getColumnIndex(KEY_BAL);
		int iWin=c.getColumnIndex(KEY_WIN);
		int iLoss=c.getColumnIndex(KEY_LOSS);
		int iDraw=c.getColumnIndex(KEY_DRAW);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result  + c.getString(iName)
					+ "\t\t" + c.getString(iBal)+ "\t\t" + c.getString(iWin) + "\t\t" + c.getString(iLoss) + "\t\t" + c.getString(iDraw) + ";";
		}
		c.close();
		return result;
	}
	
	public void updateData(String name, int bal,int win,int loss,int draw)
			throws SQLException {
		// TODO Auto-generated method stub
		ContentValues updatecv = new ContentValues();
		updatecv.put(KEY_NAME, name);
		updatecv.put(KEY_BAL, bal);
		updatecv.put(KEY_WIN, win);
		updatecv.put(KEY_LOSS, loss);
		updatecv.put(KEY_DRAW, draw);
		
		db.update(DATABASE_TABLE, updatecv, KEY_NAME + "='" + name + "'", null);
	}
	
	public String getBal(String name) {
		// TODO Auto-generated method stub
		String result="";
		String[] columns={KEY_BAL};
		Cursor c=db.query(DATABASE_TABLE, columns, KEY_NAME + "='" + name + "'", null, null, null, null);
		int iBal=c.getColumnIndex(KEY_BAL);
		c.moveToFirst();
		result=c.getString(iBal);
		c.close();		
		return result;
		
	}
	
	public long deletetAll() {
		// TODO Auto-generated method stub
		return db.delete(DATABASE_TABLE, null, null);
	}
}
