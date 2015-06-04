package ru.getlect.investme.investme.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by fj on 19.05.2015.
 */
public class InvestMeProvider extends ContentProvider {

    static final int BANKS = 100;
    static final int BANK_DEPOSITS = 102;
    static final int BANK_WITH_ID = 103;

    static final int DEPOSITS = 300;
    static final int DEPOSIT_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sQueryBuilder = new SQLiteQueryBuilder();

    static {
        sQueryBuilder.setTables(
                InvestMeContract.BanksEntry.TABLE_NAME + " INNER JOIN " +
                        InvestMeContract.DepositsEntry.TABLE_NAME + " ON " +
                        InvestMeContract.BanksEntry.TABLE_NAME + '.' + InvestMeContract.BanksEntry._ID +
                        " = " + InvestMeContract.DepositsEntry.TABLE_NAME + "." + InvestMeContract.DepositsEntry.COLUMN_BANK_ID
                        //       +                 " INNER JOIN " + InvestMeContract.BanksEntry.TABLE_NAME + "." + InvestMeContract.BanksEntry._ID +
//                        " =" + InvestMeContract.DepositsEntry.TABLE_NAME + "." +
//                        InvestMeContract.DepositsEntry.COLUMN_BANK_ABBR
        );
    } //"banks INNER JOIN deposits ON banks._ID = deposits.bank_id";


    private static final String sBankSelection = InvestMeContract.BanksEntry.TABLE_NAME
            + "." + InvestMeContract.BanksEntry._ID + " = ?";


    private static final String sBankIdDepositsSelection = InvestMeContract.BanksEntry.TABLE_NAME
            + "." + InvestMeContract.BanksEntry._ID + " = ?";


    private static final String sDepositWithIdSelection = InvestMeContract.DepositsEntry.TABLE_NAME
            + "." + InvestMeContract.DepositsEntry._ID + " = ?";

    private InvestMeDBHelper mOpenHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = InvestMeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, InvestMeContract.PATH_BANKS, BANKS);
        matcher.addURI(authority, InvestMeContract.PATH_BANKS + "/#", BANK_WITH_ID);
        matcher.addURI(authority, InvestMeContract.PATH_BANKS + "/#/" + InvestMeContract.PATH_DEPOSITS, BANK_DEPOSITS);

        matcher.addURI(authority, InvestMeContract.PATH_DEPOSITS, DEPOSITS);
        matcher.addURI(authority, InvestMeContract.PATH_DEPOSITS + "/#", DEPOSIT_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new InvestMeDBHelper(getContext());
        return false;
    }

    public Cursor getBankWithId(Uri uri, String[] projection, String sortOrder) {
        long bankId = InvestMeContract.BanksEntry.getIdFromUri(uri);
        String[] selectionArgs = {Long.toString(bankId)};
        return sQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, sBankSelection, selectionArgs, null, null, sortOrder);
    }

    public Cursor getDepositWithId(Uri uri, String[] projection, String sortOrder) {
        long bankId = InvestMeContract.DepositsEntry.getIdFromUri(uri);
        String[] selectionArgs = {Long.toString(bankId)};
        return sQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, sDepositWithIdSelection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor resCursor;
        switch (sUriMatcher.match(uri)) {
            case BANKS: {
                resCursor = mOpenHelper.getReadableDatabase().query(
                        InvestMeContract.BanksEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case BANK_DEPOSITS: {
                resCursor = getBankWithId(uri, projection, sortOrder);
                break;
            }
            case BANK_WITH_ID: {
                resCursor = getBankWithId(uri, projection, sortOrder);
                break;
            }

            case DEPOSITS: {
                resCursor = mOpenHelper.getReadableDatabase().query(
                   InvestMeContract.DepositsEntry.TABLE_NAME,
                        projection, null, null, null, null, sortOrder
                );
                break;
            }
            case DEPOSIT_WITH_ID: {
                resCursor = getDepositWithId(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("BAD URI. " + uri);
        }
        resCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return resCursor;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case BANKS:
                return InvestMeContract.BanksEntry.CONTENT_TYPE;
            case BANK_DEPOSITS:
                return InvestMeContract.DepositsEntry.CONTENT_TYPE;
            case BANK_WITH_ID:
                return InvestMeContract.BanksEntry.CONTENT_ITEM_TYPE;
            case DEPOSITS:
                return InvestMeContract.DepositsEntry.CONTENT_TYPE;
            case DEPOSIT_WITH_ID:
                return InvestMeContract.DepositsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("BAD URI. " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri resUri;
        switch (match) {
            case BANKS: {
                long _id = db.insertOrThrow(InvestMeContract.BanksEntry.TABLE_NAME, null, values);
                if (_id != -1) {
                    resUri = InvestMeContract.BanksEntry.buildBankById((int) _id);
                } else {
                    throw new SQLException("FAIL.CANT INSERT");
                }
                break;
            }
            case DEPOSITS: {
                long _id = db.insert(InvestMeContract.DepositsEntry.TABLE_NAME, null, values);
                if (_id != -1) {
                    resUri = InvestMeContract.DepositsEntry.buildDepositById( (int) _id);
                } else {
                    throw new SQLException("FAIL.CANT INSERT");
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return resUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        final int deletedRows;
        if (selection == null) selection = "1";
        switch (match) {
            case BANKS: {
                db.execSQL("DELETE FROM " + InvestMeContract.BanksEntry.TABLE_NAME);
                break;
            }
            case DEPOSITS: {
                db.execSQL("DELETE FROM " + InvestMeContract.DepositsEntry.TABLE_NAME);
                break;
            }
            default: {
                throw new UnsupportedOperationException("BAD URI" + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DEPOSITS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(InvestMeContract.DepositsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
