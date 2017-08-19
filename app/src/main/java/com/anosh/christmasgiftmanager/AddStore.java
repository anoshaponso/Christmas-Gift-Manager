package com.anosh.christmasgiftmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anosh.christmasgiftmanager.Database.DataSource;

/**
 * Created by Anosh on 11/26/2014.
 */
public class AddStore extends AppCompatActivity {


    private DataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_store);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        datasource = MainActivity.datasource;
        ImageView img = (ImageView) findViewById(R.id.storeImagea_s);
        img.setImageResource(R.drawable.store);
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
        inflater.inflate(R.menu.done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onAddStore(View v) {
        TextView storeName = (TextView) findViewById(R.id.storeNamea_s);
        if (storeName.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("You did not enter a name.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            datasource.createStore(storeName.getText().toString());
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_accept:
                TextView storeName = (TextView) findViewById(R.id.storeNamea_s);
                if (storeName.getText().toString().equals("")) {
                    new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("You did not enter a name.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    datasource.createStore(storeName.getText().toString());
                    Intent main = new Intent(this, MainActivity.class);
                    startActivity(main);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}