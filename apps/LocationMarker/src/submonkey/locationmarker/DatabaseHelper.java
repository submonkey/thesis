package submonkey.locationmarker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "locations";
	public static final String COLUMN_ID = "location_id";
	public static final String COLUMN_AREA1 = "area1";
	public static final String COLUMN_AREA2 = "area2";
	public static final String COLUMN_AREA3 = "area3";
	public static final String COLUMN_LAT = "latitude";
	public static final String COLUMN_LONG = "longitude";
	
	private static final String DATABASE_NAME = "location.db";
	private static final int DATABASE_VERSION = 1;


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String DATABASE_DDL = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_AREA1
			+ " text, " + COLUMN_AREA2 + " text, " + COLUMN_AREA3
			+ " text, " + COLUMN_LAT + " real, " + COLUMN_LONG
			+ " real);";
		db.execSQL(DATABASE_DDL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public int addLocation(Model location) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_AREA1, location.getArea1());
		values.put(COLUMN_AREA2, location.getArea2());
		values.put(COLUMN_AREA3, location.getArea3());
		values.put(COLUMN_LAT, location.getLatitude());
		values.put(COLUMN_LONG, location.getLongitude());
		
		int result = (int) db.insert(TABLE_NAME, null, values);
		db.close();
		return result;
	}
	
	public Model getLocation(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
				COLUMN_AREA1, COLUMN_AREA2, COLUMN_AREA3,
				COLUMN_LAT, COLUMN_LONG }, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) },
				null, null, null, null);
		
		if (cursor != null)
			cursor.moveToFirst();
		
		Model location = new Model(cursor.getLong(0), 
			cursor.getString(1), cursor.getString(2), cursor.getString(3), 
			cursor.getDouble(4), cursor.getDouble(5));
		return location;
	}
	
	public List<Model> getAllLocations() {
		List<Model> locationList = new ArrayList<Model>();
		String sql = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			do {
				Model location = new Model(cursor.getLong(0), 
				cursor.getString(1), cursor.getString(2), cursor.getString(3), 
				cursor.getDouble(4), cursor.getDouble(5));
				locationList.add(location);
			} while (cursor.moveToNext());
		}

		return locationList;
	}
	
//	public int updateLocation(Location location) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(COLUMN_AREA1, location.getArea1());
//		values.put(COLUMN_AREA2, location.getArea2());
//		values.put(COLUMN_AREA3, location.getArea3());
//		values.put(COLUMN_LAT, location.getLatitude());
//		values.put(COLUMN_LONG, location.getLongitude());
//	}
	
	public int deleteLocation(Model location) {
		SQLiteDatabase db = this.getWritableDatabase();
		int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(location.getId()) });
		db.close();
		return result;
	}

}
