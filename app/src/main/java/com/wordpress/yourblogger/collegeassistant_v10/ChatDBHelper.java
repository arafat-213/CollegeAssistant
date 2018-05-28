package com.wordpress.yourblogger.collegeassistant_v10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arafat-213 on 30/3/18.
 */


public class ChatDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "collegeAssistant";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_QUERIES = "chatqueries";
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";

    public ChatDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ChatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUERIES_TABLE = "CREATE TABLE " + TABLE_QUERIES + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY"
                + KEY_QUESTION + " TEXT"
                + KEY_ANSWER + " TEXT"
                + " )";
        db.execSQL(CREATE_QUERIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUERIES);

        // Create tables again
        onCreate(db);
    }


    public void addChatQuery(ChatQuery chatQuery) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, chatQuery.getmQuestion());
        values.put(KEY_ANSWER, chatQuery.getmAnswer());
        db.insert(TABLE_QUERIES, null, values);
        db.close();
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
                if (mQuestion.equals(question)) {
                    answer = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return answer;
    }
}