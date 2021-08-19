package sg.edu.rp.c346.id20011280.foodigo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "diary.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_FOOD = "Food";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_PRICE = "price";



	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE TABLE Song
		// (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
		// singers TEXT, stars INTEGER, year INTEGER );
		String createSongTableSql = "CREATE TABLE " + TABLE_FOOD + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_NAME + " TEXT, "
				+ COLUMN_DESCRIPTION + " TEXT, "
				+ COLUMN_PRICE + " INTEGER )"
                ;
		db.execSQL(createSongTableSql);
		Log.i("info", createSongTableSql + "\ncreated tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
		onCreate(db);
	}

	public long insertfood(String name, String description, double price) {
		// Get an instance of the database for writing
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);

		// Insert the row into the TABLE_SONG
		long result = db.insert(TABLE_FOOD, null, values);
		// Close the database connection
		db.close();
        Log.d("SQL Insert","" + result);
        return result;
	}

	public ArrayList<Food> getAllFood() {
		ArrayList<Food> Foodlist = new ArrayList<Food>();
		String selectQuery = "SELECT " + COLUMN_ID + ","
				+ COLUMN_NAME + "," + COLUMN_DESCRIPTION + ","
				+ COLUMN_PRICE + " FROM " + TABLE_FOOD;


		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				double price = cursor.getInt(3);



				Food newFood = new Food(id, name, description, price);
                Foodlist.add(newFood);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return Foodlist;
	}

	public int updateFood(Food data,String name,String description,double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);

        String condition = COLUMN_NAME + "= ?";
        String[] args = {data.getName()};
        int result = db.update(TABLE_FOOD, values, condition, args);
        db.close();
        return result;
    }


    public int deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_FOOD, condition, args);
        db.close();
        return result;
    }

	public ArrayList<String> getFood() {
		ArrayList<String> codes = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_PRICE};

		Cursor cursor;
		cursor = db.query(true, TABLE_FOOD, columns, null, null, null, null, null, null);
		// Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
				codes.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return codes;
	}


}
