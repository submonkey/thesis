package submonkey.locationmarker;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] columns = { 
			DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_AREA1,
			DatabaseHelper.COLUMN_AREA2,
			DatabaseHelper.COLUMN_AREA3,
			DatabaseHelper.COLUMN_LAT,
			DatabaseHelper.COLUMN_LONG
	};
	
	public DataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	

}
