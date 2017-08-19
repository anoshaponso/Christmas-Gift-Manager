package com.anosh.christmasgiftmanager.Store_Tab;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Store_Class;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.R;

import java.util.ArrayList;

/**
 * Created by Anosh on 11/11/2014.
 */
public class StoreList extends ArrayAdapter<Store_Class> {
    private final Activity context;

    ArrayList<Store_Class> store;
    ArrayList<Gift_Class> gift = MainActivity.gift;

    public StoreList(Activity context, ArrayList<Store_Class> store) {
        super(context, R.layout.store_view, store);
        this.context = context;
        this.store = store;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.store_view, null, true);
        ImageView img = (ImageView) rowView.findViewById(R.id.personImage);
        img.setImageResource(R.drawable.store);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.storeName);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.no);

        txtTitle.setText(store.get(position).getName());
        int id = store.get(position).getId();
        txtTitle1.setText("" + getNotPurchased(id) + " / " + String.valueOf(getGiftCount(id)));

        return rowView;
    }

    public int getGiftCount(int id) {
        int count = 0;
        for (int i = 0; i < gift.size(); i++) {
            if (id == (gift.get(i).getStore())) {
                count++;
            }
        }
        return count;
    }

    public int getNotPurchased(int id) {
        int count = 0;
        for (int i = 0; i < gift.size(); i++) {
            if (id == (gift.get(i).getStore())) {
                if (gift.get(i).getStatus().equals("Not purchased")) {
                    count++;
                }
            }
        }
        return count;
    }

}