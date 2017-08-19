package com.anosh.christmasgiftmanager.Tabs;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Database.DataSource;
import com.anosh.christmasgiftmanager.Group_View;
import com.anosh.christmasgiftmanager.MainActivity;
import com.anosh.christmasgiftmanager.R;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by Anosh on 11/19/2015.
 */
public class PepoleLoader extends View {
    public static MainActivity context;
    public ArrayList<Person_Class> person;
    ListView list;
    ArrayList<Integer> personIds;
    ArrayList<Group_Class> group;
    private DataSource datasource;

    public PepoleLoader(Context context, ArrayList<Integer> personIds, ArrayList<Group_Class> group) {
        super(context);
        PepoleLoader.context = (MainActivity) context;
        this.personIds = personIds;
        this.person = MainActivity.person;
        datasource = MainActivity.datasource;
        this.group = group;
        init();
    }

    public PepoleLoader(Context context, ArrayList<Group_Class> group) {
        super(context);
        PepoleLoader.context = (MainActivity) context;
        this.person = MainActivity.person;
        this.group = group;
        init1();
    }

    public void init1() {

        PeopleList1 adapter = new PeopleList1(context, person);
        list = (ListView) context.findViewById(R.id.group_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "You Clicked at " + (person.get(position).getName()), Toast.LENGTH_SHORT).show();
                String name = getPersonName(person.get(position).getId());
                float budget = getPersonBudget(person.get(position).getId());
                int personId = person.get(position).getId();

                Intent i = new Intent(context, Group_View.class);
                i.putExtra("name", name);
                i.putExtra("budget", budget);
                i.putExtra("id", personId);
                context.startActivity(i);
            }
        });
//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            public boolean onItemLongClick(AdapterView<?> arg0, View v,
//                                           final int position, long arg3) {
//                // TODO Auto-generated method stub
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                String[] array = {"Delete Group"};
//                builder.setTitle("Options")
//                        .setItems(array, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                final int p = position-1;
//                                final int id1 = (int) group.get(p).getId();
//                                datasource.deleteGroup(id1);
//                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                                builder.setMessage("Do you want to delete this Birthday?")
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                datasource.deleteGroup(id1);
//                                                PeopleFragment.newInstance();
//                                            }
//                                        })
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                // User cancelled the dialog
//                                            }
//                                        });
//                                builder.show();
//
//                            }
//                        }).show();
//
//                return true;
//            }
//        });


    }


    public void init() {

        PeopleList adapter = new PeopleList(context, personIds, person);
        list = (ListView) context.findViewById(R.id.group_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "You Clicked at " + getPersonName(personIds.get(position)), Toast.LENGTH_SHORT).show();
                String name = getPersonName(personIds.get(position));
                float budget = getPersonBudget(personIds.get(position));
                int personId = personIds.get(position);

                Intent i = new Intent(context, Group.class);
                i.putExtra("name", name);
                i.putExtra("budget", budget);
                i.putExtra("id", personId);
                context.startActivity(i);
            }

        });
//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            public boolean onItemLongClick(AdapterView<?> arg0, View v,
//                                           final int position, long arg3) {
//                // TODO Auto-generated method stub
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                String[] array ={"Delete Group"};
//                builder.setTitle("Options")
//                        .setItems(array,new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                final int p = position-1;
//                                final int id1= (int) group.get(p).getId();
//                                datasource.deleteGroup(id1);
//                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                                builder.setMessage("Do you want to delete this Birthday?")
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                datasource.deleteGroup(id1);
//                                                PeopleFragment.newInstance();
//                                            }
//                                        })
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                // User cancelled the dialog
//                                            }
//                                        });
//                                builder.show();
//
//                            }}).show();
//
//                return true;
//            }});

    }


    public String getPersonName(int id) {
        String name = null;
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getId())) {
                name = person.get(i).getName();
                break;
            }
        }
        return name;
    }

    public float getPersonBudget(int id) {
        float budget = 0;
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getId())) {
                budget = person.get(i).getBudget();
                break;
            }
        }
        return budget;
    }
}