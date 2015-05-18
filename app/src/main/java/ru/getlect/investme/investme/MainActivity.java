package ru.getlect.investme.investme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import ru.getlect.investme.investme.data.InvestMeDBHelper;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    LinearLayout ll_calculate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InvestMeDBHelper dbHelper = new InvestMeDBHelper(this);
        ll_calculate= (LinearLayout) findViewById(R.id.ll_calculate);
        ll_calculate.setOnClickListener(this);





    }


    @Override
    public void onClick(View v) {
        Intent intent_calculate = new Intent (this, ru.getlect.investme.investme.CalculatorActivity.class);
        switch (v.getId()) {
            case R.id.ll_calculate:
                intent_calculate = new Intent(this, CalculatorActivity.class);
                break;

            default:
                break;
        }
        startActivity(intent_calculate);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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
