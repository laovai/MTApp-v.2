package com.example.user.myapplication3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear;
    EditText etName, etPhone, etPassword;

    DBHelper dbHelper;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }


    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);
                cv.put("password", password);
                cv.put("phone", phone);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("user", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("user", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int phoneColIndex = c.getColumnIndex("phone");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "id = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", phone = " + c.getString(phoneColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("user", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }
}
