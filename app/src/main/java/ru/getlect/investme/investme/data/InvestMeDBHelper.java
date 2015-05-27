package ru.getlect.investme.investme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fj on 14.05.2015.
 */
public class InvestMeDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "investme.db";
    private static final int DB_VERSION = 7;



    public InvestMeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_BANKS_TABLE = "CREATE TABLE " + InvestMeContract.BanksEntry.TABLE_NAME +
                " (" + InvestMeContract.BanksEntry._ID + " INTEGER PRIMARY KEY," +
                InvestMeContract.BanksEntry.COLUMN_BANK_ABBR + " TEXT UNIQUE NOT NULL," +
                InvestMeContract.BanksEntry.COLUMN_BANK_FULL_NAME + " TEXT NOT NULL" +
                " );";


        final String SQL_CREATE_DEPOSITS_TABLE = "CREATE TABLE " + InvestMeContract.DepositsEntry.TABLE_NAME +
                " (" +
                InvestMeContract.DepositsEntry._ID + " INTEGER PRIMARY KEY," +
                InvestMeContract.DepositsEntry.COLUMN_BANK_ID + " INTEGER NOT NULL," +

                InvestMeContract.DepositsEntry.COLUMN_DEPOSIT_NAME + " TEXT NOT NULL," +
                InvestMeContract.DepositsEntry.COLUMN_MAX_RATE + " INTEGER NOT NULL," +
                InvestMeContract.DepositsEntry.COLUMN_MIN_AMOUNT + " INTEGER," +
                InvestMeContract.DepositsEntry.COLUMN_MIN_PERIOD_DAYS + " INTEGER," +
                InvestMeContract.DepositsEntry.COLUMN_CAPITALIZATION + " INTEGER DEFAULT 0," +
                InvestMeContract.DepositsEntry.COLUMN_REPLENISHMENT + " INTEGER DEFAULT 0," +
                InvestMeContract.DepositsEntry.COLUMN_WITHDRAWAL + " INTEGER DEFAULT 0," +
                InvestMeContract.DepositsEntry.COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +

                //Set up the bank column as a foreign key to bank table
                " FOREIGN KEY (" + InvestMeContract.DepositsEntry.COLUMN_BANK_ID + ") REFERENCES " +
                InvestMeContract.BanksEntry.TABLE_NAME + " (" + InvestMeContract.BanksEntry._ID + ") " +

                " );";

        db.execSQL(SQL_CREATE_BANKS_TABLE);
        db.execSQL(SQL_CREATE_DEPOSITS_TABLE);

    }


    //Update DB version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InvestMeContract.BanksEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InvestMeContract.DepositsEntry.TABLE_NAME);
        onCreate(db);
    }


}

