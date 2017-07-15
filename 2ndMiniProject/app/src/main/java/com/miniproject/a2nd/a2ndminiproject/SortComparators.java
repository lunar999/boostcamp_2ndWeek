package com.miniproject.a2nd.a2ndminiproject;

import com.miniproject.a2nd.a2ndminiproject.data.Restaurant;

import java.util.Comparator;

/**
 * Created by jh on 17. 7. 11.
 * 정렬타입 별 Comparator
 * 거리순(오름차순:최상위-가장 가까운 것), 인기순(내림차순:최상위-가장 인기있는 것), 시간순(내림차순:최상위-최근)
 */

public class SortComparators {
    public static final int SORT_DISTANCE       = 0;    // 거리순
    public static final int SORT_POPULAR        = 1;    // 인기순
    public static final int SORT_TIME           = 2;    // 시간순


    public static Comparator<Restaurant> getSortComparator(int comparatorType) {
        switch (comparatorType) {
            case SORT_DISTANCE :        return distanceComparator;
            case SORT_POPULAR:          return popularComparator;
            case SORT_TIME :            return timeComparator;
            default:                    return null;
        }
    }



    private static Comparator<Restaurant> distanceComparator = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return o1.getDistance() - o2.getDistance();     // 오름차순
        }
    };
    private static Comparator<Restaurant> popularComparator = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return o2.getPopularity() - o1.getPopularity(); // 내림차순
        }
    };
    private static Comparator<Restaurant> timeComparator = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return o2.getTime().compareTo(o1.getTime());    // 내림차순
        }
    };
}
