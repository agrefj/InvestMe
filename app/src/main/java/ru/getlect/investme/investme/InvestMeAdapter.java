package ru.getlect.investme.investme;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by fj on 27.05.2015.
 */
public class InvestMeAdapter extends CursorAdapter {





    private final int VIEW_REGULAR_DEPOSIT = 1;

    public InvestMeAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId =  R.layout.list_item_deposit;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;

    }





    public static class ViewHolder {
        public final TextView deposit;
        public final TextView maxRate;
        public final TextView minAmount;
        public final TextView minPeriod;
        public final CheckBox capitalization;
        public final CheckBox replenishment;
        public final CheckBox withdrawal;


        public ViewHolder(View view) {
            deposit = (TextView) view.findViewById(R.id.tv_deposit);
            maxRate = (TextView) view.findViewById(R.id.tv_maxRate);
            minAmount = (TextView) view.findViewById(R.id.tv_minAmount);
            minPeriod = (TextView) view.findViewById(R.id.tv_minPeriod);
            capitalization = (CheckBox) view.findViewById(R.id.capitalization);
            replenishment = (CheckBox) view.findViewById(R.id.replenishment);
            withdrawal = (CheckBox) view.findViewById(R.id.withdrawal);
        }

    }





    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.deposit.setText(cursor.getString(InvestMeFragment.COL_DEPOSIT));
        viewHolder.maxRate.setText(cursor.getString(InvestMeFragment.COL_MAXRATE));
        viewHolder.minAmount.setText(cursor.getString(InvestMeFragment.COL_MIN_AMOUNT));
        viewHolder.minPeriod.setText(cursor.getString(InvestMeFragment.COL_MIN_PERIOD));
    }
}
