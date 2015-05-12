package ru.getlect.investme.investme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //Fake data for test
        String[] ratingArray = {
                "Мкб - Мультивалютный - 18%",
                "Сбер - Как всегда лучший ваще - 2%",
                "Альфа - Для самцов - 14%",
                "Банк деревни Кукуево - Все для своих - 64%"
        };

        List<String> depositRating = new ArrayList<String>(Arrays.asList(ratingArray));

        mRatingAdapter = new ArrayAdapter<String>(
                //current context
                getActivity(),
                //id of list item layout
                R.id.listview_rating;
                //id of text view


        )


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
