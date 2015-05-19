package ru.getlect.investme.investme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by fj on 17.05.2015.
 */
public class CalculatorActivity extends ActionBarActivity implements
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    Spinner spinner_replenishment;
    CheckBox checkBox_replenishment;
    CheckBox checkBox_capitalization;
    LinearLayout ll_replenisment1;
    LinearLayout ll_replenisment2;
    String[] replenishment_period ;
    EditText et_amountOfDeposit;
    EditText et_rateOfInterest;
    EditText et_periodOfDeposit;
    EditText et_replenishmentAmount;
    CardView calculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_calc);

        setTitle(R.string.deposit_calculator);

        checkBox_capitalization = (CheckBox)findViewById(R.id.checkBox_capitalization);

        checkBox_replenishment = (CheckBox) findViewById(R.id.checkBox_replenishment);
        checkBox_replenishment.setOnCheckedChangeListener(this);


        ll_replenisment1 = (LinearLayout) findViewById(R.id.ll_replenisment1);
        ll_replenisment2 = (LinearLayout) findViewById(R.id.ll_replenisment2);

        ll_replenisment1.setVisibility(View.INVISIBLE);
        ll_replenisment2.setVisibility(View.INVISIBLE);

        et_amountOfDeposit = (EditText) findViewById(R.id.et_amountOfDeposit);
        et_rateOfInterest = (EditText) findViewById(R.id.et_rateOfInterest);
        et_periodOfDeposit = (EditText) findViewById(R.id.et_periodOfDeposit);
        et_replenishmentAmount = (EditText) findViewById(R.id.et_replenishmentAmount);

        calculate = (CardView)findViewById(R.id.calculate);
        calculate.setOnClickListener(this);

        replenishment_period = getResources().getStringArray(R.array.deposit_period);

        //Adapter for spinner
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, replenishment_period);

        spinner_replenishment = (Spinner) findViewById(R.id.spinner_replenishment);
        spinner_replenishment.setAdapter(spinner_adapter);
        spinner_replenishment.setSelection(0);
        spinner_replenishment.setPrompt("Частота пополнения");
        spinner_replenishment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(checkBox_replenishment.isChecked()){
            ll_replenisment1.setVisibility(View.VISIBLE);
            ll_replenisment2.setVisibility(View.VISIBLE);
        }
        else {
            ll_replenisment1.setVisibility(View.INVISIBLE);
            ll_replenisment2.setVisibility(View.INVISIBLE);
        }

    }

    public boolean NecessaryDataCheck(){
        final int necessaryLines=3;
        int filledLines=0;
        if (et_amountOfDeposit.getText().toString().trim().length() > 0){
            filledLines++;
        }
        if(et_rateOfInterest.getText().toString().trim().length() > 0){
            filledLines++;
        }
        if(et_periodOfDeposit.getText().toString().trim().length() > 0){
            filledLines++;
        }

        if(filledLines==necessaryLines){
            return true;
        }
        else{
            return false;
        }



    }

    public float Calculation() {
        float amount;
        float rate;
        float period;
        float replenishment = 0;
        float result = 0;


        String amountToParse = et_amountOfDeposit.getText().toString();
        amount = Float.parseFloat(amountToParse);

        String rateToParse = et_rateOfInterest.getText().toString();
        rate = Float.parseFloat(rateToParse);

        String periodToParse = et_periodOfDeposit.getText().toString();
        period = Float.parseFloat(periodToParse);

        if (et_replenishmentAmount.getText().toString().trim().length() > 0) {
            String replenishmentToParse = et_replenishmentAmount.getText().toString();
            replenishment = Float.parseFloat(replenishmentToParse);
        }

        if (!checkBox_capitalization.isChecked()) {
            if (!checkBox_replenishment.isChecked()) {
                result = amount + (amount * (rate / 100) * (period / 365));
            } else {
                float monthlyBonus = amount * (rate / 100 / 12);
                int selected = spinner_replenishment.getSelectedItemPosition();
                switch (selected) {
                    case 0:
                        for (float i = period; i >= 30; i = i - 30) {
                            amount = amount + monthlyBonus;
                            amount = amount + replenishment * 4;

                        }
                        result = amount;
                        break;
                    case 1:
                        for (float i = period; i >= 30; i = i - 30) {
                            amount = amount + monthlyBonus;
                            amount = amount + replenishment;

                        }
                        result = amount;
                        break;
                    case 2:
                        int innerCounter = 0;
                        for (float i = period; i >= 30; i = i - 30) {
                            amount = amount + monthlyBonus;
                            innerCounter++;
                            if (innerCounter == 3 || innerCounter == 6 || innerCounter == 9 ||
                                    innerCounter == 12 || innerCounter == 15 || innerCounter == 18) {
                                amount = amount + replenishment;
                            }


                        }
                        result = amount;
                        break;
                }

            }


        }
        if (checkBox_capitalization.isChecked()) {
            if (!checkBox_replenishment.isChecked()) {
                for (float i = period; i >= 30; i = i - 30) {
                    amount = amount + ((amount * (rate / 100) / 12));
                }

                result = amount;
            }

            if (checkBox_replenishment.isChecked()) {
                float monthlyBonus = amount * (rate / 100 / 12);
                int selected = spinner_replenishment.getSelectedItemPosition();
                switch (selected) {
                    case 0:
                        for (float i = period; i >= 30; i = i - 30) {

                            amount = amount + ((amount * (rate / 100) / 12));
                            amount = amount + replenishment * 4;

                        }
                        result = amount;
                        break;
                    case 1:
                        for (float i = period; i >= 30; i = i - 30) {
                            amount = amount + ((amount * (rate / 100) / 12));
                            amount = amount + replenishment;

                        }
                        result = amount;
                        break;
                    case 2:
                        int innerCounter = 0;
                        for (float i = period; i >= 30; i = i - 30) {
                            amount = amount + ((amount * (rate / 100) / 12));
                            innerCounter++;
                            if (innerCounter == 3 || innerCounter == 6 || innerCounter == 9 ||
                                    innerCounter == 12 || innerCounter == 15 || innerCounter == 18) {
                                amount = amount + replenishment;
                            }


                        }
                        result = amount;
                        break;


                }

            }





        }

        return result;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calculate:
                boolean necessaryLines =NecessaryDataCheck();
                if(necessaryLines==true) {
                    float calculationResult = Calculation();
                    String textToShow = Float.toString(calculationResult);

                    boolean invested_capitalization=false;
                    if(checkBox_capitalization.isChecked()){
                        invested_capitalization=true;
                    }
                    boolean invested_replenishment=false;
                    if(checkBox_replenishment.isChecked()){
                        invested_replenishment=true;
                    }

                    Intent intent_deposit_result = new Intent(this, CalculatorResult.class);
                    intent_deposit_result.putExtra("invested_amount",et_amountOfDeposit.getText().toString());
                    intent_deposit_result.putExtra("invested_rate",et_rateOfInterest.getText().toString());
                    intent_deposit_result.putExtra("invested_period", et_periodOfDeposit.getText().toString());
                    intent_deposit_result.putExtra("invested_capitalization",invested_capitalization);
                    intent_deposit_result.putExtra("invested_replenishment",invested_replenishment);
                    intent_deposit_result.putExtra("receive_amount", calculationResult);


                    startActivity(intent_deposit_result);

                }
                else {
                    Toast.makeText(this, R.string.calculate_data_warning, Toast.LENGTH_LONG).show();
                }
                break;

        }



    }
}




