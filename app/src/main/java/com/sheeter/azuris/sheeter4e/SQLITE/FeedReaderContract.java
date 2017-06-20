package com.sheeter.azuris.sheeter4e.SQLITE;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Powers";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
    }
}