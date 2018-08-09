package net.simplifiedcoding.bottomnavigationexample;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pang on 25/4/2561.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Restaurant.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Food";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FOOD_NAME = "name";
    public static final String COLUMN_FOOD_DETAIL = "detail";
    public static final String COLUMN_FOOD_PRICE = "price";
    public static final String COLUMN_FOOD_STAMP = "stamp";
    public static final String COLUMN_FOOD_IMAGE = "image";
    Context context;
    Food food;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FOOD_NAME + " TEXT NOT NULL, " +
                COLUMN_FOOD_DETAIL + " TEXT NOT NULL, " +
                COLUMN_FOOD_PRICE + " NUMBER NOT NULL, " +
                COLUMN_FOOD_STAMP + " NUMBER NOT NULL, " +
                COLUMN_FOOD_IMAGE + " BLOB NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean saveNewFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(COLUMN_FOOD_NAME, food.getName());
            values.put(COLUMN_FOOD_DETAIL, food.getDetail());
            values.put(COLUMN_FOOD_PRICE, food.getPrice());
            values.put(COLUMN_FOOD_STAMP, food.getStamp());
            values.put(COLUMN_FOOD_IMAGE, food.getImage());
           long result = db.insert(TABLE_NAME,null, values);
            db.close();
            if(result == -1){
                return false;
            }else {
                return true;
            }
    }

    /**Query records, give options to filter results**/
    public List<Food> foodList() {

            //regular query
        String query = "SELECT  * FROM " + TABLE_NAME;

        List<Food> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                 food = new Food();

                food.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                food.setName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME)));
                food.setDetail(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_DETAIL)));
                food.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_PRICE)));
                food.setStamp(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_STAMP)));
                food.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_FOOD_IMAGE)));
                personLinkedList.add(food);
            } while (cursor.moveToNext());
        }
        return personLinkedList;
    }

    /**Query only 1 record**/
    public Food getFood(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Food receivedFood = new Food();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedFood.setName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME)));
            receivedFood.setDetail(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_DETAIL)));
            receivedFood.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_PRICE)));
            receivedFood.setStamp(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_STAMP)));
            receivedFood.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_FOOD_IMAGE)));
        }
        return receivedFood;
    }

    /**update record**/
    public void updateFoodRecord(long personId, Context context, Food updated) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        String sql ="UPDATE "+TABLE_NAME+" SET name = ?,detail = ?,price = ?,stamp = ?,image = ? WHERE _id = ?";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1,updated.getName());
        statement.bindString(2,updated.getDetail());
        statement.bindString(3,updated.getPrice());
        statement.bindString(4,updated.getStamp());
        statement.bindBlob(5,updated.getImage());
        statement.bindLong(6,personId);

        statement.execute();
        db.close();


        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }

    /**delete record**/
    public void deleteFoodRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
}
