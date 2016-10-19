package com.kogi.socialnetworks.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kogi.socialnetworks.R;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void buttonStart(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
