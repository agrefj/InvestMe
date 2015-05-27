package ru.getlect.investme.investme.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fj on 14.05.2015.
 */
public class InvestMeContract {

    public static final String DEPOSITS_URL = "http://159.253.23.26/investme/depositsQuery.json";

    public static final String CONTENT_AUTHORITY = "ru.getlect.investme.investme";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BANKS = "banks";
    public static final String PATH_DEPOSITS = "deposits";



    public static final class BanksEntry implements BaseColumns {

        public static final String TABLE_NAME = "banks";

        public static final String COLUMN_BANK_ABBR = "bank_abbr";
        public static final String COLUMN_BANK_FULL_NAME = "bank_full_name";



        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BANKS).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BANKS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BANKS;


        public static Uri buildBanksUri() {
            return CONTENT_URI;
        }

        public static Uri buildBankById(int bankId){
            return ContentUris.withAppendedId(CONTENT_URI, bankId);
        }

        public static Uri buildDepositsOfBank(int bankId) {
            Uri bankUri = buildBankById(bankId);
            return bankUri.buildUpon().appendPath(PATH_DEPOSITS).build();
        }


        public static Long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

    public static final class DepositsEntry implements BaseColumns {

        public static final String TABLE_NAME ="deposits";
        public static final String COLUMN_BANK_ID = "bank_id";
        public static final String COLUMN_DEPOSIT_NAME = "deposit_name";
        public static final String COLUMN_MAX_RATE = "max_rate";
        public static final String COLUMN_MIN_AMOUNT = "min_amount";
        public static final String COLUMN_MIN_PERIOD_DAYS = "min_period_days";
        public static final String COLUMN_CAPITALIZATION = "capitalization";
        public static final String COLUMN_REPLENISHMENT = "replenishment";
        public static final String COLUMN_WITHDRAWAL = "withdrawal";
        public static final String COLUMN_CREATED_AT = "created_at";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPOSITS).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEPOSITS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEPOSITS;;

        public static Uri buildDepositsUri() {
            return CONTENT_URI;
        }

        public static Uri buildDepositById(int depositId) {
            return ContentUris.withAppendedId(CONTENT_URI, depositId);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }


}
