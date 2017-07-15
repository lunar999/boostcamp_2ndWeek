package com.miniproject.a2nd.a2ndminiproject;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.miniproject.a2nd.a2ndminiproject.data.Restaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    // RecyclerView Layout 설정
    private static final int LAYOUT_LINEAR      = 1;
    private static final int LAYOUT_STAGE       = 2;


    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.sort_tab) TabLayout mSortTabs;
    @BindView(R.id.bt_view_change) ImageButton mLayoutChangeView;
    @BindView(R.id.restaurant_list) RecyclerView mRestaurantViews;


    // For RecyclerView
    private RestaurantAdapter mRestaurantAdapter;
    private ArrayList<Restaurant> mRestaurants;
    private StaggeredGridLayoutManager mGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initNavigationView();
        initContents();
    }

    // AppBar - NavigationView 설정
    private void initNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSortTabs.addOnTabSelectedListener(this);
    }

    // Contents 설정
    private void initContents() {
        // mRestaurants 데이터 생성
        mRestaurants = makeDummyData();
        Collections.sort(mRestaurants, SortComparators.getSortComparator(SortComparators.SORT_DISTANCE));

        // RecyclerView 설정
        mRestaurantAdapter = new RestaurantAdapter(this, mRestaurants);
        mRestaurantViews.setHasFixedSize(true);
        mGridLayoutManager = new StaggeredGridLayoutManager(LAYOUT_STAGE, StaggeredGridLayoutManager.VERTICAL);
        mRestaurantViews.setLayoutManager(mGridLayoutManager);
        mRestaurantViews.setAdapter(mRestaurantAdapter);
    }



    // 임시 데이터 생성
    private ArrayList<Restaurant> makeDummyData() {
        String[] names = getResources().getStringArray(R.array.restaurant_names);
        String[] contents = getResources().getStringArray(R.array.restaurant_contents);
        TypedArray imageIds = getResources().obtainTypedArray(R.array.restaurant_images);
        int[] distances = getResources().getIntArray(R.array.restaurant_distances);
        int[] populars = getResources().getIntArray(R.array.restaurant_populars);

        ArrayList<Restaurant> dummyDatas = new ArrayList<>();
        Calendar calendar= Calendar.getInstance();
        for(int i=0; i<names.length; i++) {
            // new Restaurant(이름, 내용, 이미지Id, 체크여부, 거리, 인기, 작성시간)
            dummyDatas.add(new Restaurant(names[i], contents[i], imageIds.getResourceId(i, 0), false,
                    distances[i], populars[i], calendar.getTime()));
            calendar.add(Calendar.DATE, -1); // 하루씩 변경
        }
        return dummyDatas;
    }


    // RecyclerView 레이아웃 형태 변경
    @OnClick(R.id.bt_view_change)
    void onToggleLayoutManager() {
        if(mGridLayoutManager.getSpanCount() == LAYOUT_LINEAR) {
            mGridLayoutManager.setSpanCount(LAYOUT_STAGE);
            mLayoutChangeView.setImageResource(R.drawable.img_linear);
        } else {
            mGridLayoutManager.setSpanCount(LAYOUT_LINEAR);
            mLayoutChangeView.setImageResource(R.drawable.img_grid);
        }
    }


    // drawer 켜져있을 때 앱 꺼짐 방지
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // NavigationView.OnNavigationItemSelectedListener
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_layout_change :
                onToggleLayoutManager();
                break;
            case R.id.nav_distance :
                mSortTabs.getTabAt(SortComparators.SORT_DISTANCE).select();
                break;
            case R.id.nav_popularity :
                mSortTabs.getTabAt(SortComparators.SORT_POPULAR).select();
                break;
            case R.id.nav_time :
                mSortTabs.getTabAt(SortComparators.SORT_TIME).select();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // TabLayout.OnTabSelectedListener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Collections.sort(mRestaurants, SortComparators.getSortComparator(tab.getPosition()));
        mRestaurantAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
