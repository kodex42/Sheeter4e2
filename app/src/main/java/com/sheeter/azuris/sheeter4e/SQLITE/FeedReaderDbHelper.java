package com.sheeter.azuris.sheeter4e.SQLITE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String[] availableClasses = {"cleric", "fighter", "ranger", "rogue"};
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_REQUIREMENT + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TRIGGER + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUSTAIN_ACTION + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_WEAPON_BONUS + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PRIMARY_TARGET + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_TARGET + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_ATTACK + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_HIT + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_TARGET + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_ATTACK + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_HIT + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_RANGE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_HIT_EFFECTS + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_MISS_EFFECTS + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_EFFECTS + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SPECIAL_EFFECTS + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_11 + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_21 + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PARAGON + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PREREQUISITES + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private Context ctx;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;

        getWritableDatabase().execSQL(SQL_DELETE_ENTRIES);
        getWritableDatabase().execSQL(SQL_CREATE_ENTRIES);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList query(String s) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_REQUIREMENT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TRIGGER,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUSTAIN_ACTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_WEAPON_BONUS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PRIMARY_TARGET,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_TARGET,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_ATTACK,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_HIT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_TARGET,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_ATTACK,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_HIT,
                FeedReaderContract.FeedEntry.COLUMN_NAME_RANGE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_HIT_EFFECTS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_MISS_EFFECTS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_EFFECTS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SPECIAL_EFFECTS,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_11,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_21,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PARAGON,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PREREQUISITES
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { s };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry._ID + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // The table to query
                projection,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                selectionArgs,                              // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        ArrayList items = new ArrayList();
        while(cursor.moveToNext()) {
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_REQUIREMENT, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_REQUIREMENT))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_TRIGGER, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TRIGGER))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_SUSTAIN_ACTION, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUSTAIN_ACTION))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_WEAPON_BONUS, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_WEAPON_BONUS))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIMARY_TARGET, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIMARY_TARGET))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_TARGET, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_TARGET))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_ATTACK, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_ATTACK))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_HIT, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_HIT))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_TARGET, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_TARGET))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_ATTACK, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_ATTACK))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_HIT, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_HIT))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_RANGE, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_RANGE))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_HIT_EFFECTS, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_HIT_EFFECTS))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_MISS_EFFECTS, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_MISS_EFFECTS))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_EFFECTS, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_EFFECTS))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_SPECIAL_EFFECTS, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SPECIAL_EFFECTS))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_11, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_11))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_21, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_21))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_PARAGON, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PARAGON))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION))));
            items.add(new Column(FeedReaderContract.FeedEntry.COLUMN_NAME_PREREQUISITES, cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PREREQUISITES))));
        }
        cursor.close();

        return items;
    }

    public void initDb() {
        // TODO: Insert all new powers right here
        for (final String fClass : availableClasses) {
            insertPowersIntoDB(fClass);
        }
    }

    private void insertPowersIntoDB(String fClass) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        String paragon = "";
        BufferedReader reader = null;
        String line;
        try {
            InputStream ins = ctx.getResources().openRawResource(ctx.getResources().getIdentifier(fClass, "raw", ctx.getPackageName()));
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("~"))
                    paragon = line.split("~")[1];
                else
                    if (!paragon.equals("")) {
                        String[] attr = line.split(",");

                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, attr[0].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_REQUIREMENT, attr[1].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TRIGGER, attr[2].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUSTAIN_ACTION, attr[3].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_WEAPON_BONUS, attr[4].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIMARY_TARGET, attr[5].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_TARGET, attr[6].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_ATTACK, attr[7].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_HIT, attr[8].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_TARGET, attr[9].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_ATTACK, attr[10].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_HIT, attr[11].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RANGE, attr[12].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HIT_EFFECTS, attr[13].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MISS_EFFECTS, attr[14].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_EFFECTS, attr[15].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SPECIAL_EFFECTS, attr[16].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_11, attr[17].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_21, attr[18].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, attr[19].replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PARAGON, paragon.replaceAll("%", ","));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PREREQUISITES, attr[20].replaceAll("%", ","));

                        // Insert the new row, returning the primary key value of the new row
                        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                    }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}