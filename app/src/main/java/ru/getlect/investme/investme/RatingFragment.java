package ru.getlect.investme.investme;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fj on 13.05.2015.
 */
public class RatingFragment extends Fragment{

    //Fake data for test
    String[] ratingArray = {
            "Мкб - Мультивалютный - 18%",
            "Сбер - Как всегда лучший ваще - 2%",
            "Альфа - Для самцов - 14%",
            "Банк деревни Кукуево - Все для своих - 64%"
    };

    List<String> depositRating = new ArrayList<String>(Arrays.asList(ratingArray));


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }




}

