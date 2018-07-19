package vn.luckybets.broadcastsmsbanking.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.luckybets.broadcastsmsbanking.model.Bank;

public class BankHelper {

    public static String TABLE_NAME = "BANK_TABLE";
    public static String COLUMN_ID = "COLUMN_ID";
    public static String COLUMN_NAME = "COLUMN_NAME";
    public static String COLUMN_PHONE = "COLUMN_PHONE";
    public static String COLUMN_BALANCE = "COLUMN_BALANCE";
    public static String COLUMN_TIME_UPDATE = "COLUMN_TIME_UPDATE";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"
                    + COLUMN_BALANCE + " DOUBLE,"
                    + COLUMN_TIME_UPDATE + " LONG"
                    + ")";

    private DataBaseHelper helper;

    public BankHelper(Context context) {
        this.helper = new DataBaseHelper(context);
    }


    public long addBank(Bank bank) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // no need to add them
        values.put(COLUMN_NAME, bank.getName());
        values.put(COLUMN_PHONE, bank.getPhone());
        values.put(COLUMN_BALANCE, bank.getBalance());
        values.put(COLUMN_TIME_UPDATE, System.currentTimeMillis());

        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public ArrayList<Double> getTotalBank() {
        ArrayList<Double> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Log.d("ttt", "getcolum: " + cursor);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long updateBalance(long idUpdate, double balance) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // no need to add them
        values.put(COLUMN_BALANCE, balance);
        values.put(COLUMN_TIME_UPDATE, System.currentTimeMillis());
        // insert row
        long id = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{idUpdate + ""});

        // close db connection
        db.close();

        // return update row id
        return id;
    }

    public Long updatePhone(long idUpdate, String phone) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // no need to add them
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_TIME_UPDATE, System.currentTimeMillis());
        // insert row
        long id = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{idUpdate + ""});
        // close db connection
        db.close();
        // return update row id
        return id;
    }

    public Bank getBank(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_BALANCE, COLUMN_TIME_UPDATE},
                COLUMN_ID + " = ? ",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // prepare note object
            Bank bank = new Bank(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)),
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TIME_UPDATE))
            );

            // close the db connection
            cursor.close();
            return bank;
        }

        return null;
    }

    private static final String TAG = "BankHelper";

    public Bank getBankByPhone(String phone) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();


        String query = "Select * from " + TABLE_NAME + " where " + COLUMN_PHONE + " like '" + phone.replace("+", "") + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Log.e(TAG, "getBankByPhone: " + cursor.getCount());
            Bank bank = null;
            // prepare note object
            try {
                bank = new Bank(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)));

            } catch (Exception e) {
                e.printStackTrace();
            }
            // close the db connection
            cursor.close();
            return bank;
        }
        if (cursor != null) cursor.close();

        return null;
    }

    public Bank getBankByName(String name) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_BALANCE, COLUMN_TIME_UPDATE},
                COLUMN_PHONE + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // prepare note object
            Bank bank = new Bank(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)),
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TIME_UPDATE))
            );

            // close the db connection
            cursor.close();
            return bank;
        }

        return null;
    }

    public boolean isPhoneBank(String phone) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID},
                COLUMN_PHONE + "=?",
                new String[]{String.valueOf(phone)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            // prepare note object
            boolean isbank = cursor.getColumnCount() > 0;
            // close the db connection
            cursor.close();
            return isbank;
        }
        if (cursor != null) cursor.close();

        return false;
    }

    public List<Bank> getAllBank() {
        List<Bank> banks = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Bank bank = new Bank(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)));
                banks.add(bank);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        // return notes list
        return banks;
    }

    public int getCount() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
