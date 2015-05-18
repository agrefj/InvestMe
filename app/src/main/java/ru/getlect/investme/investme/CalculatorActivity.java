package ru.getlect.investme.investme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * Created by fj on 17.05.2015.
 */
public class CalculatorActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener {

    Spinner spinner_replenishment;
    CheckBox checkBox_replenishment;
    LinearLayout ll_replenisment1;
    LinearLayout ll_replenisment2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_calc);

        spinner_replenishment = (Spinner) findViewById(R.id.spinner_replenishment);

        checkBox_replenishment = (CheckBox) findViewById(R.id.checkBox_replenishment);
        checkBox_replenishment.setOnCheckedChangeListener(this);


        ll_replenisment1 = (LinearLayout) findViewById(R.id.ll_replenisment1);
        ll_replenisment2 = (LinearLayout) findViewById(R.id.ll_replenisment2);

        ll_replenisment1.setVisibility(View.INVISIBLE);
        ll_replenisment2.setVisibility(View.INVISIBLE);


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(checkBox_replenishment.isChecked()) {
            ll_replenisment1.setVisibility(View.VISIBLE);
            ll_replenisment2.setVisibility(View.VISIBLE);
        }
        else {
            ll_replenisment1.setVisibility(View.INVISIBLE);
            ll_replenisment2.setVisibility(View.INVISIBLE);
        }

    }
}




