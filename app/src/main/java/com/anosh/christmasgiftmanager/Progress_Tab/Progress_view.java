package com.anosh.christmasgiftmanager.Progress_Tab;

import android.app.Activity;
import android.os.Bundle;

import com.anosh.christmasgiftmanager.R;


/**
 * Created by adminslt on 12/5/2014.
 */
public class Progress_view extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_view);
        String status = getIntent().getExtras().getString("status");
        new ProgressGift_Loader(this, status);
    }
}