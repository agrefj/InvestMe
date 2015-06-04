package ru.getlect.investme.investme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import ru.getlect.investme.investme.data.InvestMeDBHelper;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    LinearLayout ll_calculate;
    LinearLayout ll_currency;
    LinearLayout ll_rating;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);




        InvestMeDBHelper dbHelper = new InvestMeDBHelper(this);
        ll_calculate= (LinearLayout) findViewById(R.id.ll_calculate);
        ll_calculate.setOnClickListener(this);

        ll_currency = (LinearLayout) findViewById(R.id.ll_currency);
        ll_currency.setOnClickListener(this);

        ll_rating = (LinearLayout) findViewById(R.id.ll_rating);
        ll_rating.setOnClickListener(this);





    }


    @Override
    public void onClick(View v) {
        Intent intent_main_activity = getIntent() ;
        switch (v.getId()) {
            case R.id.ll_calculate:
                intent_main_activity = new Intent(this, CalculatorActivity.class);
                break;

            case R.id.ll_currency:
                intent_main_activity = new Intent(this, CurrencyActivity.class);
                break;

            case R.id.ll_rating:
                intent_main_activity = new Intent(this, DepositsTopActivity.class);
                break;


            default:
                break;
        }
        startActivity(intent_main_activity);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
