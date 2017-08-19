package com.anosh.christmasgiftmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Database.DataSource;

import java.util.ArrayList;

/**
 * Created by Anosh on 12/11/2014.
 */
public class Group_Update extends AppCompatActivity {
    ArrayList<Group_Class> group = MainActivity.group;

    ArrayList<Person_Class> person = MainActivity.person;
    Activity context = this;
    int group_id;
    TextView groupName;
    TextView groupBudget;
    private DataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_update);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        datasource = MainActivity.datasource;
        groupName = (TextView) findViewById(R.id.groupNamea_gu);
        groupBudget = (TextView) findViewById(R.id.groupBudgeta_gu);
        String groupName1 = getIntent().getExtras().getString("name");
        groupName.setText(groupName1);
        group_id = getGroup(groupName1);
        setBudget(group_id);

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.done_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.done:
                groupBudget = (TextView) findViewById(R.id.groupBudgeta_gu);
                float budget = Float.parseFloat(groupBudget.getText().toString());
                if (notEnough(group_id, budget) == false) {
                    int index = 0;
                    for (int i = 0; i < group.size(); i++) {
                        if (group_id == (group.get(i).getId())) {
                            index = group.indexOf(group.get(i));
                        }
                    }

                    group.get(index).setBudget(budget);
                    datasource.updateGroup(group_id, budget);

                    Intent pers = new Intent(this, MainActivity.class);
                    startActivity(pers);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Not Enough Budget")
                            .setMessage("Your Budget is not enough.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return true;

            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete group")
                        .setMessage("Are you sure you want to delete this group?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < group.size(); i++) {
                                    if (group_id == (group.get(i).getId())) {
                                        removePerson(group.get(i).getId());
                                        group.remove(group.get(i));
                                        break;
                                    }
                                }
                                datasource.deleteGroup(group_id);
                                Intent main = new Intent(context, MainActivity.class);
                                startActivity(main);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void removePerson(int id) {
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getGroup())) {
                person.remove(i);
            }
        }
    }

    public int getGroup(String name) {
        int id = 0;
        for (int i = 0; i < group.size(); i++) {
            if (name.equals(group.get(i).getName())) {
                id = group.get(i).getId();
                break;
            }
        }
        return id;
    }

    public void setBudget(int id) {
        for (int i = 0; i < group.size(); i++) {
            if (id == (group.get(i).getId())) {
                groupBudget.setText(String.valueOf(group.get(i).getBudget()));

            }
        }

    }

    public boolean notEnough(int id, double budget) {
        boolean notEnough = true;
        double groupBudget = 0.0;
        for (int i = 0; i < group.size(); i++) {
            if (id == (group.get(i).getId())) {
                groupBudget = (budget);
                break;
            }
        }
        double personsBudget = 0.0;
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getGroup())) {
                personsBudget = personsBudget + (person.get(i).getBudget());
            }
        }

        if (personsBudget <= groupBudget) {
            notEnough = false;
        }
        return notEnough;
    }
}