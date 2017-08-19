package com.anosh.christmasgiftmanager.Progress_Tab;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.R;

import java.util.ArrayList;

/**
 * Created by Anosh on 11/11/2014.
 */
public class NotPurchasedGiftList extends ArrayAdapter<Gift_Class> {
    private final Activity context;
    ArrayList<Gift_Class> gift = MainActivity.gift;
    ArrayList<Gift_Class> notPurchest;
    ArrayList<Gift_Class> purchest;
    ArrayList<Gift_Class> wrapped;
    ArrayList<Gift_Class> shipped;
    ArrayList<Gift_Class> orderd;
    ArrayList<Person_Class> person = MainActivity.person;
    View rowView;

    public NotPurchasedGiftList(Activity context, ArrayList<Gift_Class> notPurchest) {
        super(context, R.layout.progress_giftview, notPurchest);
        this.notPurchest = notPurchest;
        this.context = context;


    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(R.layout.progress_giftview, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.giftNamepr_g);
        txtTitle.setText(notPurchest.get(position).getName());

        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.giftPricepr_g);
        txtTitle1.setText(getPerson(notPurchest.get(position).getPersonId()));
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.personNamepr_g);
        txtTitle2.setText(String.valueOf(notPurchest.get(position).getPrice()));
        return rowView;
//
    }

    public String getPerson(int id) {
        String name = "";
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getId())) {
                name = person.get(i).getName();
                break;
            }
        }
        return name;
    }
}
