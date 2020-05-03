package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    EditText etSearch;
    ImageView ivSearch;
    int noOfCols = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);


        ivSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendSearchQuery();
            }
        });

    }

    public void sendSearchQuery () {
        Intent intent = new Intent ( MainActivity.this, GalleryView.class );
        intent.putExtra ( "searchQuery", etSearch.getText().toString() );
        intent.putExtra("noOfCols", noOfCols);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu_inflator=getMenuInflater();
        menu_inflator.inflate(R.menu.menu_main_xml,menu);
        menu.findItem(R.id.col2).setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.col2:
                noOfCols = 2;
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.col3:
                noOfCols = 3;
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.col4:
                noOfCols = 4;
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
