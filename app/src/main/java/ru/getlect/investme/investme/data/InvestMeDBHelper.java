package ru.getlect.investme.investme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fj on 14.05.2015.
 */
public class InvestMeDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "investme.db";
    private static final int DB_VERSION = 1;


    public InvestMeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_BANKS_TABLE = "CREATE TABLE " + InvestMeContract.BanksEntry.TABLE_NAME +
                " (" + InvestMeContract.BanksEntry._ID + " INTEGER PRIMARY KEY," +
                InvestMeContract.BanksEntry.COLUMN_BANK_ID + " INTEGER," +
                InvestMeContract.BanksEntry.COLUMN_BANK_ABBR + " TEXT UNIQUE NOT NULL," +
                InvestMeContract.BanksEntry.COLUMN_BANK_FULL_NAME + " TEXT UNIQUE NOT NULL" +
                " );";


        final String SQL_CREATE_DEPOSITS_TABLE = "CREATE TABLE " + InvestMeContract.DepositsEntry.TABLE_NAME +
                " (" + InvestMeContract.DepositsEntry._ID + " INTEGER PRIMART KEY," +
                InvestMeContract.DepositsEntry.COLUMN_BANK_ID + " INTEGER UNIQIE NOT NULL," +
                InvestMeContract.DepositsEntry.COLUMN_DEPOSIT_NAME + " TEXT NOT NULL," +
                InvestMeContract.DepositsEntry.COLUMN_MAX_RATE + " INTEGER NOT NULL," +
                InvestMeContract.DepositsEntry.COLUMN_MIN_AMOUNT + " INTEGER," +
                InvestMeContract.DepositsEntry.COLUMN_MIN_PERIOD_DAYS + " INTEGER," +
                InvestMeContract.DepositsEntry.COLUMN_CAPITALIZATION + " BOOLEAN DEFAULT FALSE," +
                InvestMeContract.DepositsEntry.COLUMN_REPLENISHMENT + " BOOLEAN DEFAULT FALSE," +
                InvestMeContract.DepositsEntry.COLUMN_WITHDRAWAL + " BOOLEAN DEFAULT FALSE" +
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






