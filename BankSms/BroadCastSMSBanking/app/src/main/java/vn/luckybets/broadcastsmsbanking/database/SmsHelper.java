package vn.luckybets.broadcastsmsbanking.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;

public class SmsHelper {
//
//    private long id;
//    private String content;
//    private String phone;
//    private int action;
//    private int status;
//    private String syntax;
//    private String fullconte;
//    private String account;
//    private double balance;
//    private double moneytrans;

    public static String TABLE_NAME = "SMS_TABLE";
    public static String COLUMN_ID = "COLUMN_ID";
    public static String COLUMN_CONTENT = "COLUMN_CONTENT";
    public static String COLUMN_PHONE = "COLUMN_PHONE";
    public static String COLUMN_ACTION = "COLUMN_ACTION";
    public static String COLUMN_STATUS = "COLUMN_STATUS";
    public static String COLUMN_SYNTAX = "COLUMN_SYNTAX";
    public static String COLUMN_FULL_CONTENT = "COLUMN_FULL_CONTENT";
    public static String COLUMN_ACCOUNT = "COLUMN_ACCOUNT";
    public static String COLUMN_BALANCE = "COLUMN_BALANCE";
    public static String COLUMN_MONEYTRANS = "COLUMN_MONEYTRANS";
    public static String COLUMN_TIME = "COLUMN_TIME";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CONTENT + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"
                    + COLUMN_ACTION + " INTEGER,"
                    + COLUMN_STATUS + " INTEGER,"
                    + COLUMN_SYNTAX + " TEXT,"
                    + COLUMN_ACCOUNT + " TEXT,"
                    + COLUMN_FULL_CONTENT + " TEXT,"
                    + COLUMN_BALANCE + " DOUBLE,"
                    + COLUMN_MONEYTRANS + " DOUBLE,"
                    + COLUMN_TIME + "  DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    private DataBaseHelper helper;

    public SmsHelper(Context context) {
        this.helper = new DataBaseHelper(context);
    }


    public long addSMSBank(SmsBank sms) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // no need to add them
        values.put(COLUMN_CONTENT, sms.getContent());
        values.put(COLUMN_PHONE, sms.getPhone());
        values.put(COLUMN_ACTION, sms.getAction());
        values.put(COLUMN_STATUS, sms.getStatus());
        values.put(COLUMN_SYNTAX, sms.getSyntax());
        values.put(COLUMN_FULL_CONTENT, sms.getFullconte());
        values.put(COLUMN_ACCOUNT, sms.getAccount());
        values.put(COLUMN_BALANCE, sms.getBalance());
        values.put(COLUMN_MONEYTRANS, sms.getMoneytrans());

        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long updateStatusSMSBank(long idSms, int status) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // no need to add them
        values.put(COLUMN_STATUS, status);

        // insert row
        long id = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{idSms + ""});

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public SmsBank getSmsBank(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // prepare note object
            SmsBank smsBank = new SmsBank(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ACTION)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SYNTAX)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_FULL_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_MONEYTRANS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TIME))
            );

            // close the db connection
            cursor.close();
            return smsBank;
        }

        return null;
    }


    public List<SmsBank> getAllSmsBank() {
        List<SmsBank> smsbanks = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" ORDER BY datetime("+COLUMN_TIME+") DESC ";

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SmsBank smsBank = new SmsBank(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ACTION)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SYNTAX)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FULL_CONTENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_MONEYTRANS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TIME))
                );
                smsbanks.add(smsBank);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        // return notes list
        return smsbanks;
    }
}
