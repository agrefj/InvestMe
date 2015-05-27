package ru.getlect.investme.investme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fj on 21.05.2015.
 */
public class CurrencyActivity extends ActionBarActivity implements View.OnClickListener {

    TextView tv_dollar_cur;
    TextView tv_euro_cur;
    TextView tv_gold_cur;
    TextView tv_silver_cur;
    TextView tv_oil_cur;

    String[] euroCurrenciesArray = new String[5];
    String[] euroDatesArray = new String[5];
    String[] dollarCurrenciesArray = new String[5];
    String[] dollarDatesArray = new String[5];
    String[] oilCurrenciesArray = new String[5];
    String[] oilDatesArray = new String[5];
    String[] goldCurrenciesArray = new String[5];
    String[] goldDatesArray = new String[5];
    String[] silverCurrenciesArray = new String[5];
    String[] silverDatesArray = new String[5];

    LinearLayout currncy_ll_dollar;
    LinearLayout currncy_ll_euro;
    LinearLayout currncy_ll_gold;
    LinearLayout currncy_ll_silver;
    LinearLayout currncy_ll_oil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_currencies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.currency);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        tv_dollar_cur = (TextView)findViewById(R.id.tv_dollar_cur);
        tv_euro_cur = (TextView)findViewById(R.id.tv_euro_cur);
        tv_gold_cur = (TextView)findViewById(R.id.tv_gold_cur);
        tv_silver_cur = (TextView)findViewById(R.id.tv_silver_cur);
        tv_oil_cur = (TextView)findViewById(R.id.tv_oil_cur);

        currncy_ll_dollar = (LinearLayout) findViewById(R.id.currncy_ll_dollar);
        currncy_ll_euro = (LinearLayout) findViewById(R.id.currncy_ll_euro);
        currncy_ll_gold = (LinearLayout) findViewById(R.id.currncy_ll_gold);
        currncy_ll_silver = (LinearLayout) findViewById(R.id.currncy_ll_silver);
        currncy_ll_oil = (LinearLayout) findViewById(R.id.currncy_ll_oil);

        FetchCurrenciesTask mt = new FetchCurrenciesTask();
        mt.execute();







    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CurrencyDetail.class);
        int id=10;
        switch (v.getId()) {
            case R.id.currncy_ll_dollar:
                intent.putExtra("Currencies", dollarCurrenciesArray);
                intent.putExtra("Dates", dollarDatesArray);
                id = 0;
                break;
            case R.id.currncy_ll_euro:
                intent.putExtra("Currencies", euroCurrenciesArray);
                intent.putExtra("Dates", euroDatesArray);
                id = 1;
                break;
            case R.id.currncy_ll_gold:
                intent.putExtra("Currencies", goldCurrenciesArray);
                intent.putExtra("Dates", goldDatesArray);
                id = 2;
                break;
            case R.id.currncy_ll_silver:
                intent.putExtra("Currencies", silverCurrenciesArray);
                intent.putExtra("Dates", silverDatesArray);
                id = 3;
                break;
            case R.id.currncy_ll_oil:
                intent.putExtra("Currencies", oilCurrenciesArray);
                intent.putExtra("Dates", oilDatesArray);
                id = 4;
                break;
        }
        intent.putExtra("ID", id);
        startActivity(intent);


    }


    public class FetchCurrenciesTask extends AsyncTask<Void,Void,String>{

        private final String LOG_TAG = FetchCurrenciesTask.class.getSimpleName();


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String currenciesJsonStr = null;

        @Override
        protected String doInBackground(Void... params) {


            try {

                URL url = new URL("http://159.253.23.26/investme/currenciesQuery.json");

                // Create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                currenciesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
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
            return currenciesJsonStr;
        }


        @Override
        protected void onPostExecute(String currenciesJsonStr){
        super.onPostExecute(currenciesJsonStr);

            currenciesJsonStr = currenciesJsonStr.replaceAll("\n", "");
            currenciesJsonStr = currenciesJsonStr.replaceAll("\t", "");
            currenciesJsonStr = currenciesJsonStr.replaceAll(" ", "");



            final String REP_CURRENCIES = "currency";
            final String REP_CODE = "code";
            final String REP_DATA = "data";
            final String REP_RATE ="rate";
            final String REP_DATE = "date";



            try {
                JSONObject currenciesJSON = new JSONObject(currenciesJsonStr);
                JSONArray currenciesArray = currenciesJSON.getJSONArray(REP_CURRENCIES);

                JSONObject euroObject = currenciesArray.getJSONObject(0);
                JSONArray euroDataArray = euroObject.getJSONArray(REP_DATA);

                JSONObject dollarObject = currenciesArray.getJSONObject(1);
                JSONArray dollarDataArray = dollarObject.getJSONArray(REP_DATA);

                JSONObject oilObject = currenciesArray.getJSONObject(2);
                JSONArray oilDataArray = oilObject.getJSONArray(REP_DATA);

                JSONObject goldObject = currenciesArray.getJSONObject(3);
                JSONArray goldDataArray = goldObject.getJSONArray(REP_DATA);

                JSONObject silverObject = currenciesArray.getJSONObject(4);
                JSONArray silverDataArray = silverObject.getJSONArray(REP_DATA);


                for(int i = 0; i < 4; i++ ){
                    JSONObject euroCurObject= euroDataArray.getJSONObject(i);
                    euroCurrenciesArray[i] = euroCurObject.getString(REP_RATE);
                    euroDatesArray[i] = euroCurObject.getString(REP_DATE);

                    JSONObject dollarCurObject= dollarDataArray.getJSONObject(i);
                    dollarCurrenciesArray[i] =  dollarCurObject.getString(REP_RATE);
                    dollarDatesArray[i] =  dollarCurObject.getString(REP_DATE);

                    JSONObject oilCurObject= oilDataArray.getJSONObject(i);
                    oilCurrenciesArray[i] = oilCurObject.getString(REP_RATE);
                    oilDatesArray[i] = oilCurObject.getString(REP_DATE);

                    JSONObject goldCurObject= goldDataArray.getJSONObject(i);
                    goldCurrenciesArray[i] = goldCurObject.getString(REP_RATE);
                    goldDatesArray[i] = goldCurObject.getString(REP_DATE);

                    JSONObject silverCurObject= silverDataArray.getJSONObject(i);
                    silverCurrenciesArray[i] = silverCurObject.getString(REP_RATE);
                    silverDatesArray[i] = silverCurObject.getString(REP_DATE);

                }


                Displayer();


            }



            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        currncy_ll_dollar.setOnClickListener(this);
        currncy_ll_euro.setOnClickListener(this);
        currncy_ll_gold.setOnClickListener(this);
        currncy_ll_silver.setOnClickListener(this);
        currncy_ll_oil.setOnClickListener(this);
    }


    public void Displayer(){
        tv_euro_cur.setText(euroCurrenciesArray[0]);
        float euroCurrent = Float.parseFloat(euroCurrenciesArray[0]);
        float euroBefore = Float.parseFloat(euroCurrenciesArray[1]);
        if(euroCurrent>euroBefore){
            tv_euro_cur.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(euroCurrent<euroBefore){
            tv_euro_cur.setTextColor(getResources().getColor(R.color.currency_down));
        }


        tv_dollar_cur.setText(dollarCurrenciesArray[0]);

        float dollarCurrent = Float.parseFloat(dollarCurrenciesArray[0]);
        float dollarBefore = Float.parseFloat(dollarCurrenciesArray[1]);
        if(dollarCurrent>dollarBefore){
            tv_dollar_cur.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(dollarCurrent<dollarBefore){
            tv_dollar_cur.setTextColor(getResources().getColor(R.color.currency_down));
        }



        float oilCurrent = Float.parseFloat(oilCurrenciesArray[0]);
        float oilBefore = Float.parseFloat(oilCurrenciesArray[1]);
        if(oilCurrent>oilBefore){
            tv_oil_cur.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(oilCurrent<oilBefore){
            tv_oil_cur.setTextColor(getResources().getColor(R.color.currency_down));
        }
        float oilInRubles = oilCurrent * dollarCurrent;
        tv_oil_cur.setText(String.valueOf(oilInRubles));




        float silverCurrent = Float.parseFloat(silverCurrenciesArray[0]);
        float silverBefore = Float.parseFloat(silverCurrenciesArray[1]);
        if(silverCurrent>silverBefore){
            tv_silver_cur.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(silverCurrent<silverBefore){
            tv_silver_cur.setTextColor(getResources().getColor(R.color.currency_down));
        }

        float silverInRubles = silverCurrent * dollarCurrent;
        tv_silver_cur.setText(String.valueOf(silverInRubles));


        float goldCurrent = Float.parseFloat(goldCurrenciesArray[0]);
        float goldBefore = Float.parseFloat(goldCurrenciesArray[1]);
        if(goldCurrent>goldBefore){
            tv_gold_cur.setTextColor(getResources().getColor(R.color.currency_up));
        }
        if(goldCurrent<goldBefore){
            tv_gold_cur.setTextColor(getResources().getColor(R.color.currency_down));
        }
        float goldInRubles = goldCurrent * dollarCurrent;
        tv_gold_cur.setText(String.valueOf(goldInRubles));


    }



}


