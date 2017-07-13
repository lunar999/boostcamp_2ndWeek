package com.project2.mini.yunkyun.miniproject2;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.rv) RecyclerView recyclerView;
    @BindView(R.id.btn_distance) ImageButton btnDistance;
    @BindView(R.id.btn_popularity) ImageButton btnPopularity;
    @BindView(R.id.btn_recent) ImageButton btnDate;
    @BindView(R.id.btn_change_layout) ImageButton btnChangeLayout;

    private RecyclerAdapter adapter;
    private boolean layoutFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();
        setRecyclerView();
        setDrawer();

        initContents();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initContents() {
        String[] titleList = getResources().getStringArray(R.array.store_name_array);
        String[] descList = getResources().getStringArray(R.array.store_desc_array);
        TypedArray imageFileList = getResources().obtainTypedArray(R.array.store_image_file_array);
        int[] popularityList = getResources().getIntArray(R.array.store_popularity_array);
        int[] dateList = getResources().getIntArray(R.array.store_date_array);
        int[] distanceList = getResources().getIntArray(R.array.store_distance_array);

        ArrayList<StoreItem> items = new ArrayList<>();
        for(int i = 0; i < titleList.length; i++){
            StoreItem item = new StoreItem(titleList[i], descList[i], imageFileList.getResourceId(i, -1),
                    popularityList[i], dateList[i], distanceList[i]);
            items.add(item);
        }

        adapter.setItemList(items);
        adapter.sortItemList(RecyclerAdapter.SORT_BY_DISTANCE);
        adapter.notifyDataSetChanged();
    }

    private void changeLayoutManager() {
        RecyclerView.LayoutManager layoutManager;
        if(layoutFlag) {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        layoutFlag = !layoutFlag;
        recyclerView.setLayoutManager(layoutManager);
    }

    private void changeButtonResource(int id) {
        switch(id){
            case R.id.btn_distance:
                btnDistance.setImageResource(R.drawable.ic_distance_focus);
                btnPopularity.setImageResource(R.drawable.ic_popularity);
                btnDate.setImageResource(R.drawable.ic_recent);
                break;
            case R.id.btn_popularity:
                btnDistance.setImageResource(R.drawable.ic_distance);
                btnPopularity.setImageResource(R.drawable.ic_popularity_focus);
                btnDate.setImageResource(R.drawable.ic_recent);
                break;
            case R.id.btn_recent:
                btnDistance.setImageResource(R.drawable.ic_distance);
                btnPopularity.setImageResource(R.drawable.ic_popularity);
                btnDate.setImageResource(R.drawable.ic_recent_focus);
                break;
            case R.id.btn_change_layout:
                if(layoutFlag) {
                    btnChangeLayout.setImageResource(R.drawable.ic_change_grid);
                } else {
                    btnChangeLayout.setImageResource(R.drawable.ic_change_horizon);
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.btn_distance, R.id.btn_popularity, R.id.btn_recent, R.id.btn_change_layout})
    void onButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_distance:
                adapter.sortItemList(RecyclerAdapter.SORT_BY_DISTANCE);
                break;
            case R.id.btn_popularity:
                adapter.sortItemList(RecyclerAdapter.SORT_BY_POPULARITY);
                break;
            case R.id.btn_recent:
                adapter.sortItemList(RecyclerAdapter.SORT_BY_RECENT);
                break;
            case R.id.btn_change_layout:
                changeLayoutManager();
                break;
            default:
                break;
        }
        adapter.notifyDataSetChanged();
        changeButtonResource(id);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.nav_btn_camera:
                Toast.makeText(this, "카메라를 실행합니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_btn_gallery:
                Toast.makeText(this, "갤러리를 실행합니다.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
