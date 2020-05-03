package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryView extends AppCompatActivity {
    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    GridLayoutManager layoutManager;
    StaggeredGridLayoutManager layoutManager;
    MyAdapter adapter;
    ArrayList <ModelClass> arrayList;
    int current_page = 1;
    int doPagination = 1;
    boolean isScrolling = true;
    int currentItems;
    int totalItems;
    int scrollOutItems;
    int noOfCols = 2;
    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(GalleryView.this);
//        layoutManager= new GridLayoutManager(GalleryView.this,noOfCols);
        layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(GalleryView.this, arrayList);
        recyclerView.setAdapter(adapter);
        recyclerViewScrollListener();

        Intent i = getIntent();
        query = i.getStringExtra ( "searchQuery" );
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        jsonparse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu_inflator=getMenuInflater();
        menu_inflator.inflate(R.menu.menu_xml,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item1:
                noOfCols = 2;
//                layoutManager= new GridLayoutManager(GalleryView.this,2);
                layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
//                Toast.makeText(GalleryView.this, "item 1 has beeen selected", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(layoutManager);
                return true;

            case R.id.item2:
                noOfCols = 3;
//                layoutManager= new GridLayoutManager(GalleryView.this,3);
                layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
//                Toast.makeText(GalleryView.this, "item 2 has beeen selected", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(layoutManager);
                return true;

            case R.id.item3:
                noOfCols = 4;
//                layoutManager= new GridLayoutManager(GalleryView.this,4);
                layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
//                Toast.makeText(GalleryView.this, "item 3 has beeen selected", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(layoutManager);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void jsonparse(){
//        Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
        String searchImage = Urls.baseUrl+"&q=" + query + "&image_type=photo&page=" + current_page;
//        Toast.makeText(this, "b" + searchImage, Toast.LENGTH_SHORT).show();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, searchImage, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
//                  Toast.makeText(GalleryView.this, ""+jsonArray, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        ModelClass hit = new ModelClass();
                        hit.setWebformatURL( data.getString("webformatURL"));
                        hit.setViews(data.getInt("views"));
                        hit.setDownloads(data.getInt("downloads"));
                        hit.setFavorites(data.getInt("favorites"));
                        hit.setLikes(data.getInt("likes"));
                        hit.setComments(data.getInt("comments"));
                        hit.setUser(data.getString("user"));
                        hit.setUserImageURL(data.getString("userImageURL"));
                        Log.i("URL", i + data.getString("userImageURL"));
                        hit.setTags(data.getString("tags"));
                        arrayList.add(hit);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.i("URL error", e.toString());
//                    Toast.makeText(GalleryView.this,"Error a:" + e.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GalleryView.this,"Error b:" + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();//display the response on screen
                error.printStackTrace();
            }
        }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public void recyclerViewScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
//                scrollOutItems = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                int[] firstVisibleItems = null;
                firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                    scrollOutItems = firstVisibleItems[0];
                }

                if (doPagination == 1 && dy >= 0) {
                    if (isScrolling && (currentItems + scrollOutItems) == totalItems) {
                        isScrolling = false;
                        //Toast.makeText(getContext(), "next", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        current_page++;
                        jsonparse();
                    }
                } else {
//                    Toast.makeText(GalleryView.this, "Pagination not working", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
