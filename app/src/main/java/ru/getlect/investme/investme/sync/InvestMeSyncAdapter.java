package ru.getlect.investme.investme.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;
import ru.getlect.investme.investme.R;
import ru.getlect.investme.investme.data.InvestMeContract;


public class InvestMeSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final int SYNC_INTERVAL = 60 * 60 * 24;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    private final String LOG_TAG = InvestMeSyncAdapter.class.getSimpleName();

    public InvestMeSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        InvestMeSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {// Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private long addBank(String bankName, String bankFullName) {
        long bankId;


        Cursor bankCursor = getContext().getContentResolver().query(
                InvestMeContract.BanksEntry.CONTENT_URI,
                new String[]{InvestMeContract.BanksEntry.TABLE_NAME + "." + InvestMeContract.BanksEntry._ID},
                InvestMeContract.BanksEntry.COLUMN_BANK_ABBR + " = ?",
                new String[]{bankName},
                null
        );

        if (bankCursor.moveToNext()) {
            int IdIndex = bankCursor.getColumnIndex(InvestMeContract.BanksEntry._ID);
            bankId = bankCursor.getLong(IdIndex);
        } else {
            ContentValues values = new ContentValues();


            values.put(InvestMeContract.BanksEntry.COLUMN_BANK_ABBR, bankName);
            values.put(InvestMeContract.BanksEntry.COLUMN_BANK_FULL_NAME, bankFullName);

            Uri insertedUri = getContext().getContentResolver().insert(
                    InvestMeContract.BanksEntry.CONTENT_URI,
                    values
            );
            bankId = ContentUris.parseId(insertedUri);
        }
        return bankId;
    }

    private String[] formatResponseJSON(String JSONStr)
            throws JSONException {

        final String ARR_DEPOSITS        = "deposits";


        final String STR_BANK            = "bank";
        final String STR_BANK_FULL_NAME  = "bank_full_name";
        final String STR_DEPOSIT_NAME    = "deposit";
        final String STR_MAX_RATE        = "maxRate";
        final String STR_MIN_AMOUNT      = "minAmount";
        final String STR_MIN_PERIOD      = "minPeriod";
        final String STR_CAPITALIZATION  = "capitalization";
        final String STR_REPLENISHMENT   = "replenishment";
        final String STR_WITHDRAWAL      = "withdrawal";


        try {
            JSONObject itemsJSON = new JSONObject(JSONStr);
            JSONArray deposits = itemsJSON.getJSONArray(ARR_DEPOSITS);
            Vector<ContentValues> cVVector = new Vector<ContentValues>(deposits.length());


            Uri banksUri = InvestMeContract.BanksEntry.buildBanksUri();
            getContext().getContentResolver().delete(banksUri, null, null);
            getContext().getContentResolver().delete(InvestMeContract.DepositsEntry.buildDepositsUri(), null, null);
            for (int i = 0; i < deposits.length(); i++) {

                JSONObject depositItem = deposits.getJSONObject(i);

                long bankId = addBank(depositItem.getString(STR_BANK), depositItem.getString(STR_BANK_FULL_NAME));

                ContentValues values = new ContentValues();

                values.put(InvestMeContract.DepositsEntry.COLUMN_BANK_ID, bankId);
                values.put(InvestMeContract.DepositsEntry.COLUMN_CAPITALIZATION, depositItem.getString(STR_CAPITALIZATION));
                values.put(InvestMeContract.DepositsEntry.COLUMN_DEPOSIT_NAME, depositItem.getString(STR_DEPOSIT_NAME));
                values.put(InvestMeContract.DepositsEntry.COLUMN_MAX_RATE, depositItem.getString(STR_MAX_RATE));
                values.put(InvestMeContract.DepositsEntry.COLUMN_MIN_AMOUNT, depositItem.getString(STR_MIN_AMOUNT));
                values.put(InvestMeContract.DepositsEntry.COLUMN_MIN_PERIOD_DAYS, depositItem.getString(STR_MIN_PERIOD));
                values.put(InvestMeContract.DepositsEntry.COLUMN_REPLENISHMENT, depositItem.getString(STR_REPLENISHMENT));
                values.put(InvestMeContract.DepositsEntry.COLUMN_WITHDRAWAL, depositItem.getString(STR_WITHDRAWAL));
                cVVector.add(values);
            }

            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                getContext().getContentResolver().bulkInsert(InvestMeContract.DepositsEntry.CONTENT_URI, cvArray);
            }


            Cursor cur = getContext().getContentResolver().query(banksUri, null, null, null, null);

            cVVector = new Vector<ContentValues>(cur.getCount());
            if (cur.moveToFirst()) {
                do {
                    ContentValues cv = new ContentValues();
                    DatabaseUtils.cursorRowToContentValues(cur, cv);
                    cVVector.add(cv);
                } while (cur.moveToNext());
            }

            Log.d(LOG_TAG, "FetchTask Complete. " + cVVector.size() + " Inserted");

            String[] resultStrs = new String[cVVector.size()];

            return resultStrs;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONStr;

        try {

            Uri builtUri = Uri.parse(InvestMeContract.DEPOSITS_URL).buildUpon().build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return;
            }
            JSONStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            formatResponseJSON(JSONStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
