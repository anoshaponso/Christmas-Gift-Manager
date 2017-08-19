package com.anosh.christmasgiftmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Classes.Store_Class;
import com.anosh.christmasgiftmanager.Database.DataSource;
import com.anosh.christmasgiftmanager.Gift.Gift_Loader;

import java.util.ArrayList;

public class Group_View extends AppCompatActivity {
    String name;
    float budget;
    int personId;
    ArrayList<Store_Class> store = MainActivity.store;
    ArrayList<Person_Class> person = MainActivity.person;
    ArrayList<Gift_Class> gift = MainActivity.gift;
    String image;
    Activity context = this;
    ArrayList<Group_Class> group = MainActivity.group;
    Activity a = (Activity) this;
    Integer[] imageId = MainActivity.imageId;
    TextView spent;
    ImageView personImage;
    private DataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getIntent().getExtras().getString("name");
        budget = getIntent().getExtras().getFloat("budget");
        personId = getIntent().getExtras().getInt("id");
        setContentView(R.layout.group);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        datasource = MainActivity.datasource;
        personImage = (ImageView) findViewById(R.id.personImageg);
        loadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
        loadData();

    }

    public void loadData() {
        if (!getContact(personId).equals("")) {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(getContact(personId)));
            Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

            if (photoUri != null) {
                personImage.setImageURI(photoUri);
            } else if (!(image = getPersonImage(personId)).equals("")) {
                if (image.substring(0, 21).equals("content://com.android")) {
                    String[] photo_split = image.split("%3A");
                    image = "content://media/external/images/media/" + photo_split[1];
                }

                personImage.setImageURI(Uri.parse(image));
            } else {
                personImage.setImageResource(imageId[0]);
            }

            TextView personName = (TextView) findViewById(R.id.personNameg);
            EditText budget1 = (EditText) findViewById(R.id.budgetg);

            personName.setText(name);
            budget1.setText(String.valueOf(budget));
            new Gift_Loader(this, personId);
            spent = (TextView) findViewById(R.id.spentAmountgr);
            spent.setText("" + spent(personId));

        } else {
            TextView personName = (TextView) findViewById(R.id.personNameg);
            EditText budget1 = (EditText) findViewById(R.id.budgetg);
            personImage.setImageResource(MainActivity.imageId[0]);
            personName.setText(name);
            budget1.setText(String.valueOf(budget));
            new Gift_Loader(this, personId);
            spent = (TextView) findViewById(R.id.spentAmountgr);
            spent.setText("" + spent(personId));
        }
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    public String getPersonImage(int id) {
        String name = null;
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getId())) {
                name = person.get(i).getImage();
                break;
            }
        }
        return name;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recreate();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        new Gift_Loader(this, personId);
//        spent.setText("" + spent(personId));
//    }

    public String getContact(int id) {
        String name = "";
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getId())) {
                name = person.get(i).getContact();
                break;
            }
        }
        return name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {    // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add:
                if (store.size() != 0) {
                    Intent addGift = new Intent(this, AddGift.class);
                    addGift.putExtra("personId", personId);
                    addGift.putExtra("name", name);

                    startActivity(addGift);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("No Store")
                            .setMessage("You should add store First.")
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
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this person?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < person.size(); i++) {
                                    if (personId == (person.get(i).getId())) {
                                        person.remove(person.get(i));
                                        break;
                                    }
                                }
                                datasource.deletePerson(personId);
                                Intent in = new Intent(a, MainActivity.class);
                                startActivity(in);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            case R.id.done:
                int index = 0;
                for (int i = 0; i < person.size(); i++) {
                    if (personId == (person.get(i).getId())) {
                        index = person.indexOf(person.get(i));
                    }
                }
                EditText personBudget = (EditText) findViewById(R.id.budgetg);
                person.get(index).setBudget(Float.parseFloat(personBudget.getText().toString()));
                datasource.updatePerson(personId, person.get(index).getName(), Float.parseFloat(personBudget.getText().toString()), person.get(index).getImage());
                recreate();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public double spent(int id) {
        double total = 0;
        for (int i = 0; i < gift.size(); i++) {
            if (id == (gift.get(i).getPersonId())) {
                total = total + gift.get(i).getPrice();
            }
        }
        return total;
    }

    public int getID(String name) {
        int id = 0;
        for (int i = 0; i < group.size(); i++) {
            if (group.get(i).getName().equals(name)) {
                id = group.get(i).getId();
                break;
            }
        }
        return id;
    }
}
