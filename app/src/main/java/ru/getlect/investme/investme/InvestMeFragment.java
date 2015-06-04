package ru.getlect.investme.investme;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;

import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.getlect.investme.investme.data.InvestMeContract;
import ru.getlect.investme.investme.data.InvestMeDBHelper;

/**
 * Created by fj on 27.05.2015.
 */
public class InvestMeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    static final int COL_DEPOSIT_ID = 0;
    static final int COL_BANK_ID = 1;
    static final int COL_DEPOSIT = 2;
    static final int COL_MAXRATE = 3;
    static final int COL_MIN_AMOUNT = 4;
    static final int COL_MIN_PERIOD = 5;
    static final int COL_CAPITALIZATION = 6;
    static final int COL_REPLENISHMENT = 7;
    static final int COL_WITHDRAWAL = 8;
    static final int COL_CREATED_AT = 9;

    final String LOG_TAG = "myLogs";

    InvestMeDBHelper dbh;
    SQLiteDatabase db;




    private static final int INVESTME_LOADER_ID = 0;

    private static final String[] __COLUMNS = {
            InvestMeContract.DepositsEntry.TABLE_NAME + '.' + InvestMeContract.DepositsEntry._ID,
            InvestMeContract.DepositsEntry.COLUMN_BANK_ID,
            InvestMeContract.DepositsEntry.COLUMN_DEPOSIT_NAME,
            InvestMeContract.DepositsEntry.COLUMN_MAX_RATE,
            InvestMeContract.DepositsEntry.COLUMN_MIN_AMOUNT,
            InvestMeContract.DepositsEntry.COLUMN_MIN_PERIOD_DAYS,
            InvestMeContract.DepositsEntry.COLUMN_CAPITALIZATION,
            InvestMeContract.DepositsEntry.COLUMN_REPLENISHMENT,
            InvestMeContract.DepositsEntry.COLUMN_WITHDRAWAL,
            InvestMeContract.DepositsEntry.COLUMN_CREATED_AT,
    };



    private final String SELECTED_KEY = "LIST_VIEW_POSITION";
    private InvestMeAdapter mIMAdapter;
    private int mPosition;
    private ListView mListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dbh = new InvestMeDBHelper(getActivity());
        db =  dbh.getWritableDatabase();
        Cursor cd = db.rawQuery("SELECT * FROM banks", null);
        Cursor c = db.rawQuery("SELECT * FROM deposits", null);
        logCursor(cd);
        logCursor(c);
    }

    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mIMAdapter = new InvestMeAdapter(getActivity(), null, 0);


        View rootView = inflater.inflate(R.layout.fragment_investme, container, false);

        mListView = (ListView)rootView.findViewById(R.id.lv_Top);
        mListView.setAdapter(mIMAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(INVESTME_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri depositsUri = InvestMeContract.DepositsEntry.buildDepositsUri();

        return new CursorLoader(getActivity(),
                depositsUri,
                __COLUMNS,
                null,
                null,
                null);


    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mIMAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }
        else {
            mListView.setSelection(0);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mIMAdapter.swapCursor(null);
    }

    public interface Callback {
        public void onItemSelected(Uri classUri);
    }
}
