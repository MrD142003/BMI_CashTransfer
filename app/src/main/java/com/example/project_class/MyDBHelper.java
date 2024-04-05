package com.example.project_class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DBName = "mydb.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "Sinhvien";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String YEAROB = "yearob";
    private static final String TABLE_NAME1 = "Sinhvien_Images";
    private static String SV_ID = "sv_id";
    private static String ImgBitmap = "image_bitmap";
    private static String Place = "place";
    private static String Date = "date";

    private SQLiteDatabase myDB;

    public MyDBHelper(@Nullable Context context) {
        super(context, DBName, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryTable = "CREATE TABLE " + TABLE_NAME +
                " ( " + ID + " INTEGER PRIMARY KEY autoincrement," +
                NAME + " TEXT NOT NULL, " +
                YEAROB + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(queryTable);

        String queryTable1 = "CREATE TABLE " + TABLE_NAME1 +
                " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SV_ID + " INTEGER NOT NULL, " +
                ImgBitmap + " BLOB , " +
                Place + " TEXT , " +
                Date + " TEXT , " +
                " CONSTRAINT fk_sinhvien_images FOREIGN KEY ( " + SV_ID + " ) REFERENCES " + TABLE_NAME + " ( " + ID + " ) ON DELETE CASCADE )";
        sqLiteDatabase.execSQL(queryTable1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        if (i < 2) {
//            // Step 2: Copy existing data from the old table to the new one
//            String copyDataQuery = "INSERT INTO " + TABLE_NAME1_V2 + " (" + ID + "," + SV_ID + "," + ImgBitmap + ") SELECT (" + ID + "," + SV_ID + "," + ImgBitmap + ") FROM " + TABLE_NAME1;
//            sqLiteDatabase.execSQL(copyDataQuery);
//
//            // Step 3: Delete the old table
//            String deleteOldTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME1;
//            sqLiteDatabase.execSQL(deleteOldTableQuery);
//        }
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getYEAROB() {
        return YEAROB;
    }

    public static String getPlace() {
        return Place;
    }

    public static String getSvId() {
        return SV_ID;
    }

    public static String getDate() {
        return Date;
    }

    public static String getImgBitmap() {
        return ImgBitmap;
    }


    public String getPlace(String table_name, int id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + table_name + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndexOrThrow(Place));
        }
        return result;
    }

    public byte[] getImage(int id) {
        Cursor cursor = myDB.query(TABLE_NAME, new String[]{ImgBitmap}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(ImgBitmap));
        cursor.close();
        myDB.close();
        return imageData;
    }

    private byte[] getBitmapAsByteAray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    // insert
    public long Insert(String name, int yearob) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(YEAROB, yearob);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor DisplayAll(String table_name) {
        String query = "SELECT * FROM " + table_name;
        return myDB.rawQuery(query, null);
    }

    public Cursor DisplayAll(String table_name, int id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + table_name +" WHERE "+SV_ID+" = "+id;
        return db.rawQuery(query, null);
    }

    public int getCount() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = myDB.rawQuery(query, null);
        return cursor.getCount();
    }

    public void updateIDAfterDelete() {
        int startID = 1;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = myDB.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put(ID, startID++);
            String where = ID + " = " + cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getID()));
            myDB.update(TABLE_NAME, values, where, null);
        }
        myDB.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
    }

    public long Update(int id, String name, int yearob) {
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(YEAROB, yearob);
        String where = ID + " = " + id;
        return myDB.update(TABLE_NAME, values, where, null);
    }

    public long Delete(int id) {
        String where = ID + " = " + id;
        return myDB.delete(TABLE_NAME, where, null);
    }

    public Boolean checkIfExist(String table_name) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + table_name + "'", null);
        return cursor.getCount() > 0;
    }

    public boolean doesTableHaveForeignKey(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA foreign_key_list(" + tableName + ")", null);
        boolean hasForeignKey = cursor.getCount() > 0;
        cursor.close();
        return hasForeignKey;
    }

    public long InsertToSinhvien_Images(int sv_id, byte[] byteArray, String place, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("erq", place + "   " + date);
        values.put(SV_ID, sv_id);
        values.put(ImgBitmap, byteArray);
        values.put(Place, place!=null?place:"VietNam");
        values.put(Date, date!=null?date:getDateTimeNow());
        return db.insert(TABLE_NAME1, null, values);
    }
    public String getDateTimeNow() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0 (January is 0)
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + dayOfMonth + " " + hourOfDay + ":" + minute + ":" + second;
    }
}
