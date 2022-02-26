package com.comp3350.recip_e.UI;

import androidx.appcompat.app.AppCompatActivity;
import com.comp3350.recip_e.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setNewView(View view)
    {
        Intent lIntent = new Intent(MainActivity.this, LeftViewActivity.class);
        MainActivity.this.startActivity(lIntent);
    }
}

