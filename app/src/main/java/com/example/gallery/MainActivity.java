package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    EditText etSearch;
    ImageView ivSearch;

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
        startActivity(intent);
    }
}
