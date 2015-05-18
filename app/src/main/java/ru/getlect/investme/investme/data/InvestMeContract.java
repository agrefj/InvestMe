package ru.getlect.investme.investme.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fj on 14.05.2015.
 */
public class InvestMeContract {

    public static final String DEPOSITS_URL = "http://159.253.23.26/DepositsQuery.json";

    public static final String CONTENT_AUTHORITY = "ru.getlect.investme.investme";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BANKS = "banks";
    public static final String PATH_DEPOSITS = "deposits";



    public static final class BanksEntry implements BaseColumns {

        public static final String TABLE_NAME = "banks";

        public static final String COLUMN_BANK_ABBR = "bank_abbr";
        public static final String COLUMN_BANK_FULL_NAME = "bank_full_name";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BANKS).build();

        public static Uri buildClassesUri() {
            return CONTENT_URI;

        }

        public static Uri buildDepositsOfBank(int bankId) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(bankId)).build();
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


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPOSITS).build();



    }

}
