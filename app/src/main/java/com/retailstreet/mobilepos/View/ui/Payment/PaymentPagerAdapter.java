package com.retailstreet.mobilepos.View.ui.Payment;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PaymentPagerAdapter extends PagerAdapter {

        private final List<Integer> pageIds = new ArrayList<>();

        public void insertViewId(@IdRes int pageId) {
            pageIds.add(pageId);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return container.findViewById(pageIds.get(position));
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return pageIds.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
            @Override
            public CharSequence getPageTitle(int position) {

            switch (position){
                case 0:
                    return "CASH";
                case 1:
                    return "CARD";
                case 2:
                    return "ONLINE";
                case 3:
                    return "CHEQUE";
                case 4:
                    return "REDEEM";
                default:
                    return "TAB "+(position+1);
            }

            }
    }
