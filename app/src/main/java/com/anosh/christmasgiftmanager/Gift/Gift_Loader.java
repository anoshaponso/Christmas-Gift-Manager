package com.anosh.christmasgiftmanager.Gift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Gift_Show;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.R;

import java.util.ArrayList;


/**
 * Created by Anosh on 11/11/2014.
 */
public class Gift_Loader extends View {

    Activity context;
    ListView list;
    ArrayList<Person_Class> person = MainActivity.person;
    ArrayList<Group_Class> group = MainActivity.group;
    int personId;
    ArrayList<Gift_Class> gift = MainActivity.gift;
    ArrayList<Gift_Class> giftlist;


    public Gift_Loader(Context context, int personId) {

        super(context);
        this.context = (Activity) context;
        this.personId = personId;
        getGifts(personId);
        init();
    }

    public void init() {

        if (giftlist.size() != 0) {
            GiftList adapter = new GiftList(context, person, giftlist);
            list = (ListView) context.findViewById(R.id.groupListViewg);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "You Clicked at " + giftlist.get(position).getName(), Toast.LENGTH_SHORT).show();

                    String name = giftlist.get(position).getName();
                    double budget = giftlist.get(position).getPrice();
                    int store = giftlist.get(position).getStore();
                    String status = giftlist.get(position).getStatus();
//                Group_View g=new Group_View(context);
                    Intent i = new Intent(context, Gift_Show.class);
                    i.putExtra("name", name);
                    i.putExtra("budget", budget);
                    i.putExtra("store", store);
                    i.putExtra("status", status);
                    i.putExtra("id", giftlist.get(position).getId());
                    i.putExtra("personId", personId);
                    context.startActivity(i);
                }
            });
        }

    }

    public ArrayList<Gift_Class> getGifts(int id) {
        giftlist = new ArrayList<Gift_Class>();
        for (int i = 0; i < gift.size(); i++) {
            if (gift.get(i).getPersonId() == (id)) {
                if (gift.get(i).getStatus().equals("Not purchased")) {
                    giftlist.add(gift.get(i));
                }
            }
        }
        for (int i = 0; i < gift.size(); i++) {
            if (gift.get(i).getPersonId() == (id)) {
                if (gift.get(i).getStatus().equals("Ordered")) {
                    giftlist.add(gift.get(i));
                }
            }
        }
        for (int i = 0; i < gift.size(); i++) {
            if (gift.get(i).getPersonId() == (id)) {
                if (gift.get(i).getStatus().equals("Shipped")) {
                    giftlist.add(gift.get(i));
                }
            }
        }
        for (int i = 0; i < gift.size(); i++) {
            if (gift.get(i).getPersonId() == (id)) {
                if (gift.get(i).getStatus().equals("Purchased")) {
                    giftlist.add(gift.get(i));
                }
            }
        }
        for (int i = 0; i < gift.size(); i++) {
            if (gift.get(i).getPersonId() == (id)) {
                if (gift.get(i).getStatus().equals("Wrapped")) {
                    giftlist.add(gift.get(i));
                }
            }
        }
        return giftlist;
    }
}