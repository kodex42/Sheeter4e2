package com.sheeter.azuris.sheeter4e.SQLITE;

/**
 * Created by Azuris on 2017-07-06.
 */

public class Column {
    private String columnName;
    private String value;

    Column(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getValue() {
        return value;
    }
}
