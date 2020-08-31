package com.example.tuku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tuku.R;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbarLienHe;
    TextView tvSĐT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        toolbarLienHe = findViewById(R.id.toolbarLienHe);
        tvSĐT = findViewById(R.id.tvSĐT);

        ActionToolBar();

        tvSĐT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0915342134"));
                startActivity(intent);
            }
        });
    }

    private void ActionToolBar() {

        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}