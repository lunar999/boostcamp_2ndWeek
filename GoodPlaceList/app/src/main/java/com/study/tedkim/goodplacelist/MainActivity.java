package com.study.tedkim.goodplacelist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton ibDrawer;   // Navigation Drawer call button
    ImageButton ibLayoutType;   // LayoutType set button
    TabLayout tlSorting;    // Article SortingType set button

    // RecyclerView components
    RecyclerView rvList;
    RecyclerView.LayoutManager mLayoutManager;
    ShopListAdapter mAdapter;

    // Article dataset
    ArrayList<ShopInfo> mDataset = new ArrayList<>();

    // Layout type state
    static final int LAYOUT_LINEAR = 0;
    static int LAYOUT_STAGGERED = 1;
    int mLayoutType = 0;

    // Sort type state
    static final int SORT_DIST = 0;
    static final int SORT_FAVOR = 1;
    static final int SORT_RECENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setViewAction();

        initData();

        setRecyclerView();

    }

    // view component 초기화
    public void initView() {

        ibDrawer = (ImageButton) findViewById(R.id.imageButton_drawer);
        ibDrawer.setOnClickListener(this);

        ibLayoutType = (ImageButton) findViewById(R.id.imageButton_layoutType);
        ibLayoutType.setOnClickListener(this);

        tlSorting = (TabLayout) findViewById(R.id.tabLayout_sorting);
    }

    // 각종 버튼 들의 동작 정의
    public void setViewAction() {

        // Sort 동작 정의
        tlSorting.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 1. 탭 이 클릭 되었을 때,
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                // 1.1 탭 아이템의 position 에 따라 sorting state 설정
                int sortingType = tab.getPosition();
                // 1.2 설정 된 sorting state 를 현재 dataset 과 함께 Compare 클래스에 전달
                Collections.sort(mDataset, new CompareShopInfo(sortingType));
                // 1.3 정렬을 마친 dataset 의 변경사항을 적용
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // TODO - Navigation Drawer Layout 구현 할 것
            // Navigation Drawer layout 호출
            case R.id.imageButton_drawer:

                break;

            // RecyclerView 의 layout type 변경
            case R.id.imageButton_layoutType:

                // 버튼 변경 시, fade_in 애니메이션 추가
                Animation buttonAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                ibLayoutType.startAnimation(buttonAnimation);

                // 1. 해당 버튼이 클릭 되었을 때,
                // 1.1 현재 레이아웃의 상태가 Linear 라면,
                if (mLayoutType == LAYOUT_LINEAR) {

                    // 1.1.2 Layout state 를 Staggered 로 변경하고,
                    mLayoutType = LAYOUT_STAGGERED;
                    // 1.1.3 StaggeredGridLayoutManager 로 교체 해 준다.
                    mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);    // 2줄로, 수직 배열
                    rvList.setLayoutManager(mLayoutManager);
                    // 1.1.4 Linear layout icon 으로 변경
                    ibLayoutType.setImageResource(R.drawable.img_linear);

                } else {

                    // 1.2.1 현재 레이아웃의 상태가 Staggered 라면,
                    mLayoutType = LAYOUT_LINEAR;
                    // 1.2.2 LinearLayoutManager 로 변경 해 준다
                    mLayoutManager = new LinearLayoutManager(this);
                    rvList.setLayoutManager(mLayoutManager);
                    // 1.2.3 Staggered layout icon 으로 변경
                    ibLayoutType.setImageResource(R.drawable.img_grid);
                }
                break;

        }
    }

    // Custom Comparator
    public class CompareShopInfo implements Comparator<ShopInfo> {

        // tab button 들로 부터 전달받은 sorting state 에 따라 정렬 로직을 달리 함
        int mSortingType;

        public CompareShopInfo(int sortingType) {
            mSortingType = sortingType;
        }

        @Override
        public int compare(ShopInfo o1, ShopInfo o2) {

            switch (mSortingType) {

                // 거리순 정렬
                case SORT_DIST:
                    return Double.compare(o1.getDist(), o2.getDist());

                // 인기순 정렬
                case SORT_FAVOR:
                    return Double.compare(o1.getFavor(), o2.getFavor());

                // TODO - 날짜 데이터 형식을 int 에서 Date 로 변경 할 것
                // 최근순 정렬
                case SORT_RECENT:
                    return o1.getRecent() - o2.getRecent();

                default:
                    return 0;
            }
        }
    }

    // DumpData 를 이용해 Article dataset 초기화
    public void initData() {

        DumpItems dumpItems = new DumpItems();
        for (int i = 0; i < dumpItems.name.length; i++) {

            ShopInfo info = new ShopInfo();

            info.setName(dumpItems.name[i]);
            info.setContents(dumpItems.contents[i]);
            info.setDist(dumpItems.dist[i]);
            info.setFavor(dumpItems.favor[i]);
            info.setRecent(dumpItems.recent[i]);
            info.setShopImage(dumpItems.shopImage[i]);

            mDataset.add(info);
        }
        // 초기 정렬 방식을 '거리순' 으로 설정 한다.
        Collections.sort(mDataset, new CompareShopInfo(SORT_DIST));

    }

    // RecyclerView init
    public void setRecyclerView() {

        rvList = (RecyclerView) findViewById(R.id.recyclerView_list);

        mAdapter = new ShopListAdapter(this, mDataset);
        rvList.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLayoutManager);
    }
}
