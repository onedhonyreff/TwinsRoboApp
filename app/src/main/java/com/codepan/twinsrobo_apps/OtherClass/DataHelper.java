package com.codepan.twinsrobo_apps.OtherClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "twinsrobotsqlite.db";
    public static final String TABLE_NAME = "session_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "id_user";
    public static final String COL_3 = "first_name_user";
    public static final String COL_4 = "last_logged";

    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, id_user TEXT, first_name_user TEXT, last_logged DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private String getCurDate(){
        Date curDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        return simpleDateFormat.format(curDate);
    }

    public boolean insertData(String idUser, String firstNameUser){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, idUser);
        contentValues.put(COL_3, firstNameUser);
        contentValues.put(COL_4, getCurDate());

        Long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public void deleteSession(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public boolean renewSession(String idUser, String firstNameUser){
        deleteSession();
        if(insertData(idUser, firstNameUser)){
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor getLoginSession(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select *, julianday(\"now\") - julianday(last_logged) as limit_session from " + TABLE_NAME + " order by id desc limit 1", null);
        return res;
    }
}
