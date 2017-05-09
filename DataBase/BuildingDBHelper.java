package com.example.danilwelter.pjabuildings.DataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class BuildingDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BuildingsDb";
    public static final String TABLE_MUSEUMS = "museums";
    public static final String TABLE_DWELLINGHOUSES = "dwellingHouses";

    public static final String KEY_ID = "_id";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_FLOORSCOUNT = "floorsCount";

    public static final String KEY_STARTTIME = "startTime";
    public static final String KEY_ENDTIME = "endTime";

    public static final String KEY_APARTMENTSCOUNT = "apartmentsCount";

    public BuildingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table " + TABLE_MUSEUMS + "(" + KEY_ID
                    + " integer primary key, " + KEY_ADDRESS + " text, " + KEY_FLOORSCOUNT + " integer, " + KEY_STARTTIME + " string, " + KEY_ENDTIME + " string" + ")");

            db.execSQL("create table " + TABLE_DWELLINGHOUSES + "(" + KEY_ID
                    + " integer primary key, " + KEY_ADDRESS + " text, " + KEY_FLOORSCOUNT + " integer, " + KEY_APARTMENTSCOUNT + " integer)");
        }catch (SQLiteException e){

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
