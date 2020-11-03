package com.example.farmersmarket.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccountModel extends SQLiteOpenHelper {

    private static String TABLE_NAME = "Account";
    private static String COLUMN_ID = "accountID";
    private static String COLUMN_TYPE_ID = "typeID";
    private static String COLUMN_PHONE = "phone";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_GENDER = "gender";
    private static String COLUMN_ADDRESS = "address";
    private static String COLUMN_EMAIL = "email";
    private static String COLUMN_IMAGE = "image";
    private static String COLUMN_STATUS = "status";

    public AccountModel(@Nullable Context context, @Nullable String name,
                        int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
