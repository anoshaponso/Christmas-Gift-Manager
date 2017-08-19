package com.anosh.christmasgiftmanager.Store_Tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.R;

import java.util.ArrayList;

/**
 * Created by adminslt on 12/4/2014.
 */
public class StoreDetails extends Activity {
    Activity context = this;
    ArrayList<Gift_Class> gift = MainActivity.gift;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_details);
        TextView txt = (TextView) findViewById(R.id.storeNames_d);
        TextView txt1 = (TextView) findViewById(R.id.no_of_gifts);
        int storeId = getIntent().getExtras().getInt("storeId");
        new StoreDetails_Loader(context, storeId);
        txt.setText(getIntent().getExtras().getString("storeName"));
        txt1.setText("No of gifts: " + gifts(storeId));
    }

    public int gifts(int id) {
        int count = 0;
        for (int i = 0; i < gift.size(); i++) {
            if (id == (gift.get(i).getStore())) {
                count++;
            }
        }
        return count;
    }
}