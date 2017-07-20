package com.example.android.inventorius.data;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventorius.data.ItemsContract.ItemEntry;

/**
 * Database helper for Inventorius app. Manages database creation and version management.
 */

public class ItemsDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemsDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "items.db";

    /**
     * Database version. If you change the database schema, you must increment the databae version.
     */
    private static final int DATABASE_VERSION = 1;

    public ItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the items tabel
        String CREATE_SQL_ITEMS_TABLE = "CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
                ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                ItemEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL DEFAULT 0, " +
                ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                ItemEntry.COLUMN_ITEM_IMAGE + " TEXT);";
        // Execute the SQL statement
        db.execSQL(CREATE_SQL_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.

    }
}
