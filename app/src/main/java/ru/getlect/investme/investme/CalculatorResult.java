package ru.getlect.investme.investme;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by fj on 19.05.2015.
 */
public class CalculatorResult extends ActionBarActivity {

    TextView tv_invested_amount;
    TextView tv_invested_rate;
    TextView tv_invested_period;
    CheckBox cb_capitalization;
    CheckBox cb_replenishment;

    TextView tv_receive_amount;
    TextView tv_receive_eir;
    TextView tv_receive_earned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_result);


        setTitle(R.string.deposit_calculator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_calculate_result);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        tv_invested_amount = (TextView)findViewById(R.id.tv_invested_amount);
        String invested_amount = getIntent().getStringExtra("invested_amount");
        tv_invested_amount.setText(invested_amount);

        TextView tv_invested_rate = (TextView)findViewById(R.id.tv_invested_rate);
        String invested_rate = getIntent().getStringExtra("invested_rate");
        tv_invested_rate.setText(invested_rate);

        TextView tv_invested_period = (TextView)findViewById(R.id.tv_invested_period);
        String invested_period = getIntent().getStringExtra("invested_period");
        tv_invested_period.setText(invested_period);

        cb_capitalization = (CheckBox)findViewById(R.id.cb_invested_capitalization);
        Boolean invested_cap = getIntent().getExtras().getBoolean("invested_capitalization");
        cb_capitalization.setChecked(invested_cap);

        cb_replenishment = (CheckBox)findViewById(R.id.cb_invested_replenishment);
        Boolean invested_rep = getIntent().getExtras().getBoolean("invested_replenishment");
        cb_replenishment.setChecked(invested_rep);

        tv_receive_amount = (TextView)findViewById(R.id.tv_receive_amount);
        Float receive_amount = getIntent().getExtras().getFloat("receive_amount");
        receive_amount = new BigDecimal(receive_amount).setScale(2, RoundingMode.UP).floatValue();
        tv_receive_amount.setText(String.valueOf(receive_amount));

        float efficient_IR = efficientInterestRate(invested_amount, receive_amount);
        tv_receive_eir = (TextView)findViewById(R.id.tv_receive_eir);
        tv_receive_eir.setText(String.valueOf(efficient_IR));

        tv_receive_earned = (TextView)findViewById(R.id.tv_receive_earned);

        tv_receive_earned.setText(String.valueOf(earnedInterest(invested_amount,receive_amount)));


    }

    public float earnedInterest(String invested_amount, Float receive_amount){
        float invested = Float.parseFloat(invested_amount);

        float earnedAmount = receive_amount-invested;
        earnedAmount = new BigDecimal(earnedAmount).setScale(2,RoundingMode.UP).floatValue();
        return earnedAmount;
    }

    public float efficientInterestRate(String invested_amount, Float receive_amount){
        float invested = Float.parseFloat(invested_amount);
        float EIR = ((receive_amount-invested)/invested)*100;

        EIR = new BigDecimal(EIR).setScale(2, RoundingMode.UP).floatValue();

        return EIR;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }




}


