/**
 * Created by Abdul on 19/06/19.
 * DB Helper class for insertion,deletion, updation and fetching data
 */
package com.abdul.sqliteassingment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String EMPLOYEE_TABLE_NAME = "employee_table";
    public static final String EMPLOYEE_COLUMN_ID = "id";
    public static final String EMPLOYEE_COLUMN_NAME = "name";
    public static final String EMPLOYEE_COLUMN_EMAIL = "email";
    public static final String EMPLOYEE_COLUMN_POSITION = "position";
    public static final String EMPLOYEE_COLUMN_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table
        db.execSQL("create table employee_table" +"(id integer primary key AUTOINCREMENT NOT NULL, name text,phone text,email text, position text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employee_table");
        onCreate(db);
    }

    //creating method to insert data into the table
    public boolean insertEmployee (String name, String phone, String email, String position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("position", position);
        db.insert("employee_table", null, contentValues);
        return true;
    }

    //method to fetch perticular data
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from employee_table where id="+id+"", null );
        return res;
    }

    //method to get the id of the selected employee
    public Cursor getId(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from employee_table where name="+name+"", null );
        return res;
    }

    //method to update record in the database
    public boolean updateEmployee (Integer id, String name, String phone, String email, String position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("position", position);
        db.update("employee_table", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    //method to delete the record of an employee
    public Integer deleteEmployee (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("employee_table","id = ? ",new String[]{String.valueOf(id)});
        return 0;
    }

    //method to fetch all the data at once from the database
    public ArrayList<String> getAllEmployees() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from employee_table", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(EMPLOYEE_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}