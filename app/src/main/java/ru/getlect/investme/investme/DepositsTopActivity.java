package ru.getlect.investme.investme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.gc.materialdesign.views.ButtonFloat;

/**
 * Created by fj on 21.05.2015.
 */
public class DepositsTopActivity extends ActionBarActivity {

   // String[] deposits = { "Подари жизнь", "Пополняй", "Сохраняй", "Все включено", "Накопительный +",
  //          "Потенциал", "Победа", "СмартВклад" };


    ButtonFloat btnFloatCurrency;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        setTitle(R.string.top_deposits);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnFloatCurrency = (ButtonFloat)findViewById(R.id.buttonFloat);

       // ListView lvTop = (ListView)findViewById(R.id.lvTop);

      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//deposits);


   // lvTop.setAdapter(adapter);



}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

}