package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    List<String> items;

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDITH_TEX_COD = 20;

    Button btnAdd;
    EditText Button;
    RecyclerView Rccler;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.button3);
        Button = findViewById(R.id.EditText);
        Rccler = findViewById(R.id.Rccler);


        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void OnItemsLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item was remove", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void OnItemsClicked(int position) {
                Log.d("MainActivity", "Sindle click position" + position);
                Intent i = new Intent(MainActivity.this, EdithActivity.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(i, EDITH_TEX_COD);
            }
        };

         itemsAdapter = new ItemsAdapter(items, onLongClickListener,onClickListener);  // TO MODIFY
        Rccler.setAdapter(itemsAdapter);
        Rccler.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = Button.getText().toString();
            //
                items.add(todoItem);
                //
                itemsAdapter.notifyItemInserted(items.size() -1);
                Button.setText("");
                Toast.makeText(getApplicationContext(), "Text is enter", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == EDITH_TEX_COD){
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            items.set(position, itemText);
            itemsAdapter.notifyItemChanged(position);
            saveItems();
            Toast.makeText(getApplicationContext(), "Items updat Succelfull", Toast.LENGTH_SHORT).show();
        }else{
            Log.w("MainActivity", "Unknow call to onActivityResult");
        }
    }

    private File getdataFile(){
        return new File(getFilesDir(), "data.text");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getdataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
           Log.e("MainActivity", "Error reading item", e);
           items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getdataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing item", e);
        }
    }
}
