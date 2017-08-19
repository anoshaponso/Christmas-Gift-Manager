package com.anosh.christmasgiftmanager.Store_Tab;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.R;

import java.util.ArrayList;

/**
 * Created by Anosh on 11/11/2014.
 */
public class StoreDetailsList extends ArrayAdapter<Gift_Class> {
    private final Activity context;

    ArrayList<Gift_Class> storeGift;


    public StoreDetailsList(Activity context, ArrayList<Gift_Class> storeGift) {
        super(context, R.layout.gift_view, storeGift);
        this.storeGift = storeGift;
        this.context = context;


    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.gift_view, null, true);
        TextView giftName = (TextView) rowView.findViewById(R.id.giftNameg_v);
        TextView giftPrice = (TextView) rowView.findViewById(R.id.budgetg_v);
        TextView giftStatus = (TextView) rowView.findViewById(R.id.giftStatusg_v);

        giftName.setText(storeGift.get(position).getName());
        giftPrice.setText(String.valueOf(storeGift.get(position).getPrice()));
        giftStatus.setText(storeGift.get(position).getStatus());


        return rowView;
    }

}