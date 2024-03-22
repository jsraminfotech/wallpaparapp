package com.jsrinfotech.wallpaparapp.TabSlider;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.jsrinfotech.wallpaparapp.fragment.Tab1Fragment;
import com.jsrinfotech.wallpaparapp.fragment.Tab2Fragment;

/**
 * Created by Jack sparrow on 24-07-2019.
 */

public class TabpageAdapter extends FragmentStatePagerAdapter {
    String[] Tabarray = new String[]{"Featured", "Category"};

    public TabpageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Tabarray[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Tab1Fragment tab1Fragment = new Tab1Fragment();
                return tab1Fragment;

            case 1:
                Tab2Fragment tab2Fragment = new Tab2Fragment();
                return tab2Fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return Tabarray.length;
    }
}
