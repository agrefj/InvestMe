package ru.getlect.investme.investme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by fj on 27.05.2015.
 */
public class CurrencyDetail extends ActionBarActivity {


    TextView tv_date_1;
    TextView tv_date_2;
    TextView tv_date_3;
    TextView tv_date_4;

    TextView tv_сurrent_value;

    TextView tv_rate_1;
    TextView tv_rate_2;
    TextView tv_rate_3;
    TextView tv_rate_4;

    TextView tv_choosed_currency;

    ImageView iv_choosed_logo;

    String[] dates;
    String[] currencies;

    private static final String INVEST_SHARE_HASHTAG = " #InvestMe";
    private String mCurrencyStr;
    private String share_info;
    private String share_currency;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_currency_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.currency);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        tv_choosed_currency = (TextView) findViewById(R.id.tv_choosed_currency);
        tv_date_1= (TextView) findViewById(R.id.tv_date_1);
        tv_date_2= (TextView) findViewById(R.id.tv_date_2);
        tv_date_3= (TextView) findViewById(R.id.tv_date_3);
        tv_date_4= (TextView) findViewById(R.id.tv_date_4);
        tv_сurrent_value = (TextView) findViewById(R.id.tv_сurrent_value);
        tv_rate_1 = (TextView) findViewById(R.id.tv_rate_1);
        tv_rate_2 = (TextView) findViewById(R.id.tv_rate_2);
        tv_rate_3 = (TextView) findViewById(R.id.tv_rate_3);
        tv_rate_4 = (TextView) findViewById(R.id.tv_rate_4);


        iv_choosed_logo = (ImageView)findViewById(R.id.iv_choosed_logo);


        int id = getIntent().getIntExtra("ID", 10);
        dates = getIntent().getStringArrayExtra("Dates");
        currencies = getIntent().getStringArrayExtra("Currencies");

        CurrentChanger(id);
        LogoChanger(id);
        DataChanger(id);



    }

    public void LogoChanger(int id){

        if(id==0){
            iv_choosed_logo.setImageResource(R.drawable.dollar);

        }
        if(id==1){
            iv_choosed_logo.setImageResource(R.drawable.euro);

        }
        if(id==2){
            iv_choosed_logo.setImageResource(R.drawable.gold);

        }
        if(id==3){
            iv_choosed_logo.setImageResource(R.drawable.silver);

        }
        if(id==4){
            iv_choosed_logo.setImageResource(R.drawable.oil);

        }

    }


    public void CurrentChanger(int id) {
        if(id==0){
            tv_choosed_currency.setText(R.string.cur_dollar);
            share_currency = getResources().getString(R.string.cur_dollar);
        }
        if(id==1){
            tv_choosed_currency.setText(R.string.cur_euro);
            share_currency = getResources().getString(R.string.cur_euro);
        }
        if(id==2){
            tv_choosed_currency.setText(R.string.cur_gold);
            share_currency = getResources().getString(R.string.cur_gold);
        }
        if(id==3){
            tv_choosed_currency.setText(R.string.cur_silver);
            share_currency = getResources().getString(R.string.cur_silver);
        }
        if(id==4){
            tv_choosed_currency.setText(R.string.cur_oil);
            share_currency = getResources().getString(R.string.cur_oil);
        }

    }

    public void DataChanger(int id){

        mCurrencyStr = String.valueOf(currencies[0]);
        tv_сurrent_value.setText(currencies[0]);

        tv_date_1.setText(dates[1]);
        tv_date_2.setText(dates[2]);
        tv_date_3.setText(dates[3]);
        tv_date_4.setText(dates[4]);

        tv_rate_1.setText(currencies[1]);
        tv_rate_2.setText(currencies[2]);
        tv_rate_3.setText(currencies[3]);
        tv_rate_4.setText(currencies[4]);

        ColorChanger();

        share_info = getResources().getString(R.string.share_info);




    }

    public void ColorChanger(){
      Float[] rates = new Float[5];
       for(int i=0; i<5; i++){
           rates[i] = Float.parseFloat(currencies[i]);
       }

        if(rates[0]>rates[1]){
            tv_сurrent_value.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(rates[0]<rates[1]){
            tv_сurrent_value.setTextColor(getResources().getColor(R.color.currency_down));
        }

        if(rates[1]>rates[2]){
           tv_rate_1.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(rates[1]<rates[2]){
            tv_rate_1.setTextColor(getResources().getColor(R.color.currency_down));
        }

        if(rates[2]>rates[3]){
            tv_rate_2.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(rates[2]<rates[3]){
            tv_rate_2.setTextColor(getResources().getColor(R.color.currency_down));
        }

        if(rates[3]>rates[4]){
            tv_rate_3.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(rates[3]<rates[4]){
            tv_rate_3.setTextColor(getResources().getColor(R.color.currency_down));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailfragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareInvestIntent());
        } else {
        }
        return true;
    }




    private Intent createShareInvestIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, share_info + " " + share_currency + " " +
                        mCurrencyStr + " " + INVEST_SHARE_HASHTAG);

        return shareIntent;
    }


}





