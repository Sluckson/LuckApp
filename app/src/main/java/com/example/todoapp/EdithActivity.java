package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.todoapp.MainActivity.KEY_ITEM_POSITION;
import static com.example.todoapp.MainActivity.KEY_ITEM_TEXT;

public class EdithActivity extends AppCompatActivity {
    EditText etItem;
    Button btnsave;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edith);

        etItem = findViewById(R.id.etItem);
        btnsave = findViewById(R.id.btnsave);
        getSupportActionBar().setTitle("edit text");

        etItem.setText(getIntent().getStringExtra(KEY_ITEM_TEXT));
        position = getIntent().getIntExtra(KEY_ITEM_POSITION, 0);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(KEY_ITEM_TEXT, etItem.getText().toString());
            intent.putExtra(KEY_ITEM_POSITION,position);
            setResult(RESULT_OK, intent);
            finish();
            }
        });
            }
}