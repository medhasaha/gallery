package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class GalleryView extends AppCompatActivity {
    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    GridLayoutManager layoutManager;
    StaggeredGridLayoutManager layoutManager;
    MyAdapter adapter;
    MyAdapter_fourCols adapter_fourCols;
    ArrayList <ModelClass> arrayList;
    int current_page = 1;
    int doPagination = 1;
    boolean isScrolling = true;
    int currentItems;
    int totalItems;
    int scrollOutItems;
    int noOfCols = 2;
    String query = "";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        Intent i = getIntent();
        query = i.getStringExtra ( "searchQuery" );
        noOfCols = i.getIntExtra("noOfCols", 2);
//        Toast.makeText(GalleryView.this, noOfCols + "", Toast.LENGTH_SHORT).show();

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(GalleryView.this);
//        layoutManager= new GridLayoutManager(GalleryView.this,noOfCols);
        layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if(noOfCols == 4) {
            adapter_fourCols = new MyAdapter_fourCols(GalleryView.this, arrayList, noOfCols);
            recyclerView.setAdapter(adapter_fourCols);
        }else{
            adapter = new MyAdapter(GalleryView.this, arrayList, noOfCols);
            recyclerView.setAdapter(adapter);
        }
        recyclerViewScrollListener();
//
        if(current_page == 1){
            firstPage();
        }else {
            jsonparse();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu_inflator=getMenuInflater();
        menu_inflator.inflate(R.menu.menu_xml,menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                // Toast like print
                query = q;
                firstPage();
//                Toast.makeText(GalleryView.this, "search1" + query, Toast.LENGTH_SHORT).show();
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        if(noOfCols == 2){
            menu.findItem(R.id.col2).setChecked(true);
        }
        else if (noOfCols == 3){
            menu.findItem(R.id.col3).setChecked(true);
        }
        else if(noOfCols == 4){
            menu.findItem(R.id.col4).setChecked(true);
        }
        else {
            menu.findItem(R.id.col2).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_search:
//                Toast.makeText(GalleryView.this, "search", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.col2:
                noOfCols = 2;
//                layoutManager= new GridLayoutManager(GalleryView.this,2);
                layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new MyAdapter(GalleryView.this, arrayList, noOfCols);
                recyclerView.setAdapter(adapter);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;

            case R.id.col3:
                noOfCols = 3;
//                layoutManager= new GridLayoutManager(GalleryView.this,3);
                layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new MyAdapter(GalleryView.this, arrayList, noOfCols);
                recyclerView.setAdapter(adapter);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;

            case R.id.col4:
                noOfCols = 4;
//                layoutManager= new GridLayoutManager(GalleryView.this,4);
                layoutManager = new StaggeredGridLayoutManager(noOfCols, LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter_fourCols = new MyAdapter_fourCols(GalleryView.this, arrayList, noOfCols);
                recyclerView.setAdapter(adapter_fourCols);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void jsonparse(){
//        Toast.makeText(this, "jsonparse", Toast.LENGTH_SHORT).show();
        String searchImage = Urls.baseUrl+"&q=" + query + "&image_type=photo&per_page=100&page=" + current_page;
//        Toast.makeText(this, "b" + query, Toast.LENGTH_SHORT).show();
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
                        hit.setPreviewURL( data.getString("previewURL"));
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
                    Log.i("URL error a:", e.toString());
//                    Toast.makeText(GalleryView.this,"Error a:" + e.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(GalleryView.this,"Error b:" + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                Log.i("URL error b:", error.toString());
                if (error instanceof NoConnectionError) {
                    Toast.makeText(GalleryView.this,"Check Your Internet Connection" , Toast.LENGTH_LONG).show();
                }
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
                        if(current_page == 1){
                            firstPage();
                        }else {
                            jsonparse();
                        }
                    }
                } else {
//                    Toast.makeText(GalleryView.this, "Pagination not working", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void firstPage(){
//        Toast.makeText(this, "First", Toast.LENGTH_SHORT).show();
        arrayList.clear();
        String searchImage = Urls.baseUrl+"&q=" + query + "&image_type=photo&page=1&per_page=100";
//        Toast.makeText(this, "b" + query, Toast.LENGTH_SHORT).show();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, searchImage, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
//                  Toast.makeText(GalleryView.this, ""+jsonArray, Toast.LENGTH_SHORT).show();
                    if (jsonArray.length() == 0){
                        Toast.makeText(GalleryView.this, "No Results Found for " + query, Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        ModelClass hit = new ModelClass();
                        hit.setWebformatURL( data.getString("webformatURL"));
                        hit.setPreviewURL( data.getString("previewURL"));
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
                    Log.i("URL error a:", e.toString());
//                    Toast.makeText(GalleryView.this,"Error a:" + e.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(GalleryView.this,"Error b:" + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                Log.i("URL error b:", error.toString());
                if (error instanceof NoConnectionError) {
                    Toast.makeText(GalleryView.this,"Check Your Internet Connection" , Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
