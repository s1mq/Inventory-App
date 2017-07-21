package com.example.android.inventorius.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API contract for the Inventorius app
 */

public final class ItemsContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor
    private ItemsContract() {
    }

    /**
     * The "Content Authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.inventorius";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to the base content URI for possible URI's).
     * For instance, content://com.example.android.items/items is a valid path for looking at
     * the item data. content://com.example.android.items/staff will fail, as the ContentProvider
     * hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_ITEMS = "items";

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     */
    public static abstract class ItemEntry implements BaseColumns {
        /**
         * The content URI to access the item data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * Name of the database table for items
         */
        public static final String TABLE_NAME = "items";

        /**
         * Unique ID number for the item (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Name of the item.
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_ITEM_NAME = "name";

        /**
         * Price of the item
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_ITEM_PRICE = "price";

        /**
         * Number of items
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_ITEM_QUANTITY = "quantity";

        /**
         * Image of the item
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_ITEM_IMAGE = "image";

    }


}
