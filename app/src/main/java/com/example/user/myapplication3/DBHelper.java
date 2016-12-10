package com.example.user.myapplication3;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        db.execSQL("CREATE TABLE user (\n" +
                "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  password TEXT NOT NULL,\n" +
                "  name TEXT NULL,\n" +
                "  phone TEXT NULL\n" +
                "  );");
        db.execSQL("CREATE TABLE dog (\n" +
                "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  birthday TEXT NULL,\n" +
                "  sex INTEGER NULL,\n" +
                "  color TEXT NULL,\n" +
                "  wool TEXT NULL,\n" +
                "  price_category TEXT NULL,\n" +
                "  status TEXT NULL,\n" +
                "  user_id INTEGER NOT NULL,\n" +
                "  CONSTRAINT fk_dog_user\n" +
                "    FOREIGN KEY (user_id)\n" +
                "    REFERENCES user (id)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION)\n" +
                ";\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

