package com.anosh.christmasgiftmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Classes.Store_Class;
import com.anosh.christmasgiftmanager.Database.DataSource;

import java.util.ArrayList;

/**
 * Created by Anosh on 11/26/2014.
 */
public class AddPerson1 extends AppCompatActivity {

    private final int PICK_CONTACT = 1;
    public ArrayList<Person_Class> person = MainActivity.person;
    public ArrayList<Gift_Class> gift;
    public ArrayList<Group_Class> group = MainActivity.group;
    public ArrayList<Store_Class> store;
    Spinner s;
    EditText name1;
    EditText budget1;
    String contactId = "";
    String image;

    private DataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        datasource = MainActivity.datasource;

        ImageView img = (ImageView) findViewById(R.id.personimageView);
        image = getIntent().getExtras().getString("image");
        TextView personName = (TextView) findViewById(R.id.personNamea_d);
        TextView personBudget = (TextView) findViewById(R.id.budgeta_d);
        personName.setText(getIntent().getExtras().getString("name"));
        personBudget.setText(getIntent().getExtras().getString("budget"));
        img.setImageURI(Uri.parse(image));
        loadSpinner();
        s.setSelection(getIntent().getExtras().getInt("id"));
    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
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

    public void loadSpinner() {
        String arr[] = new String[group.size()];
        for (int i = 0; i < group.size(); i++) {
            arr[i] = group.get(i).getName();
        }
        s = (Spinner) findViewById(R.id.groupNamea_d);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner, arr);
        s.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.done_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    // Handle presses on the action bar items
        switch (item.getItemId()) {

//            case R.id .contacts:
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAddPerson(View v) {
        TextView personName = (TextView) findViewById(R.id.personNamea_d);
        TextView personBudget = (TextView) findViewById(R.id.budgeta_d);

        Spinner s = (Spinner) findViewById(R.id.groupNamea_d);
        int groupId = getID(s.getSelectedItem().toString());
        if (personName.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("You did not enter a name.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (personBudget.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("You did not enter a budget.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            if (notEnough(groupId, Double.parseDouble(personBudget.getText().toString())) == false) {
                datasource.addPerson(personName.getText().toString(), "", Float.parseFloat(personBudget.getText().toString()), contactId, groupId);
                onBackPressed();
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
        }
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

    public void onSelect(View v) {

        TextView personName = (TextView) findViewById(R.id.personNamea_d);
        TextView personBudget = (TextView) findViewById(R.id.budgeta_d);

        String name = personName.getText().toString();
        String budget = personBudget.getText().toString();
        int id = (int) s.getSelectedItemId();

        Intent in = new Intent(this, Gallery.class);
        in.putExtra("name", name);
        in.putExtra("budget", budget);
        in.putExtra("id", id);
        startActivity(in);

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
//                        Intent in=new Intent(this,AddPerson.class);

                        contactId = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID));
                        String name = (c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                        ImageView img = (ImageView) findViewById(R.id.personimageView);
                        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
                        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                        img.setImageURI(photoUri);

                        setContentView(R.layout.add_person);
                        TextView tx = (TextView) findViewById(R.id.personNamea_d);
                        tx.setText((name));
                        loadSpinner();

                    }
                }
                break;
        }

    }

    public boolean notEnough(int id, double budget) {
        boolean notEnough = true;
        double groupBudget = 0.0;
        for (int i = 0; i < group.size(); i++) {
            if (id == (group.get(i).getId())) {
                groupBudget = (group.get(i).getBudget());
                break;
            }
        }
        double personsBudget = 0.0;
        for (int i = 0; i < person.size(); i++) {
            if (id == (person.get(i).getGroup())) {
                personsBudget = personsBudget + (person.get(i).getBudget());
            }
        }
        personsBudget = personsBudget + (budget);
        if (personsBudget <= groupBudget) {
            notEnough = false;
        }
        return notEnough;
    }
}