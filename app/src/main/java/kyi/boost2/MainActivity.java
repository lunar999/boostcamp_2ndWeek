package kyi.boost2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Item> list = new ArrayList<>();
    private ImageButton toggleLayout;
    private boolean isCard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(getResources().getString(R.string.title));
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getResources().getString(R.string.distance)));
        tabs.addTab(tabs.newTab().setText(getResources().getString(R.string.popularity)));
        tabs.addTab(tabs.newTab().setText(getResources().getString(R.string.recent)));

        toggleLayout = (ImageButton) findViewById(R.id.toggleLayout);
        toggleLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isCard) {
                    toggleLayout.setImageResource(R.drawable.set_stagger);
                    isCard ^= true;
                    layoutManager = new StaggeredGridLayoutManager(2, 1);
                    rv.setLayoutManager(layoutManager);
                }
                else {
                    toggleLayout.setImageResource(R.drawable.set_card);
                    isCard ^= true;
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    rv.setLayoutManager(layoutManager);
                }
            }
        });


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tabs.getSelectedTabPosition();
                switch (pos) {
                    case 0:
                        Collections.sort(list, new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return String.valueOf(o1.getDistance()).compareTo(String.valueOf(o2.getDistance()));
                            }
                        });
                        break;
                    case 1:
                        Collections.sort(list, new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return String.valueOf(o2.getPopular()).compareTo(String.valueOf(o1.getPopular()));
                            }
                        });
                        break;
                    case 2:
                        Collections.sort(list, new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return String.valueOf(o1.getAbstime()).compareTo(String.valueOf(o2.getAbstime()));
                            }
                        });
                        break;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Item[] item = new Item[8];
        item[0] = new Item(R.drawable.jinjin, 0.20, 20170426, 7.6, false, getResources().getString(R.string.store1_name), getResources().getString(R.string.store1_explanation));
        item[1] = new Item(R.drawable.season, 0.75, 20170630, 7.5, false, getResources().getString(R.string.store2_name), getResources().getString(R.string.store2_explanation));
        item[2] = new Item(R.drawable.cha_r, 0.85, 20170223, 9.2, false, getResources().getString(R.string.store3_name), getResources().getString(R.string.store3_explanation));
        item[3] = new Item(R.drawable.shybana, 0.65, 20170519, 8.8, false, getResources().getString(R.string.store4_name), getResources().getString(R.string.store4_explanation));
        item[4] = new Item(R.drawable.seren, 2.60, 20170704, 9.0, false, getResources().getString(R.string.store5_name), getResources().getString(R.string.store5_explanation));
        item[5] = new Item(R.drawable.butchers, 0.70, 20170607, 8.9, false, getResources().getString(R.string.store6_name), getResources().getString(R.string.store6_explanation));
        list.add(item[0]);
        list.add(item[1]);
        list.add(item[2]);
        list.add(item[3]);
        list.add(item[4]);
        list.add(item[5]);

        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return String.valueOf(o1.getDistance()).compareTo(String.valueOf(o2.getDistance()));
            }
        });

        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, 1);
        rv.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(list);
        rv.setAdapter(adapter);
    }
}
