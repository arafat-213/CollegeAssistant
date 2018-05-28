package com.wordpress.yourblogger.collegeassistant_v10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arafat-213 on 27/3/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "collegeAssistant";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_SEMESTER = "semester";
    private static final String KEY_DEPARTMENT = "department";

    // For chat queries table
    private static final String TABLE_QUERIES = "chatqueries";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";

    private String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PASS + " TEXT,"
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_SEMESTER + " TEXT"
            + ")";

    private String CREATE_QUERIES_TABLE = "CREATE TABLE " + TABLE_QUERIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_QUESTION + " TEXT,"
            + KEY_ANSWER + " TEXT"
            + ")";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERIES_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUERIES);
        // Create tables again
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUname());
        values.put(KEY_PASS, user.getPass());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }

    public String searchPass(String uname) {
        String name, pass;
        pass = "not found";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select " + KEY_NAME + " , " + KEY_PASS + " from " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);

                if (name.equalsIgnoreCase(uname)) {
                    pass = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pass;
    }

    public String displayAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        String displayData = "";
        if (cursor.moveToFirst()) {
            do {
                displayData += cursor.getString(1);
                displayData += " : " + cursor.getString(2) + "\n";
            } while (cursor.moveToNext());
        }
        cursor.close();
        return displayData;
    }

    public String askQuestion(String question) {
        String mQuestion;
        String answer = "Sorry, No appropriate answer found. Try another question";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + KEY_QUESTION + " , " + KEY_ANSWER + " from " + TABLE_QUERIES;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                mQuestion = cursor.getString(0);
                if (question.toLowerCase().contains(mQuestion.toLowerCase()))
                /*if (mQuestion.equalsIgnoreCase(question.trim()))*/ {
                    answer = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return answer;
    }

    public void addChatQuery(ChatQuery chatQuery) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, chatQuery.getmQuestion());
        values.put(KEY_ANSWER, chatQuery.getmAnswer());
        db.insert(TABLE_QUERIES, null, values);
        db.close();
    }
}