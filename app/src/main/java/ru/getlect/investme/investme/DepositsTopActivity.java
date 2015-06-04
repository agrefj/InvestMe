package ru.getlect.investme.investme;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import ru.getlect.investme.investme.sync.InvestMeSyncAdapter;

/**
 * Created by fj on 21.05.2015.
 */
public class DepositsTopActivity extends ActionBarActivity implements InvestMeFragment.Callback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits_top);

        InvestMeSyncAdapter.syncImmediately(getApplicationContext());



    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }


    @Override
    public void onItemSelected(Uri classUri) {

    }
}