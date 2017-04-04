package com.abhinavgautam.abouttime;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by New on 30-12-2016.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
         if(index%7==0)
             index=0;
        else if((index-1)%7==0)
             index=1;
         else if((index-2)%7==0)
             index=2;
         else if((index-3)%7==0)
             index=3;
         else if((index-4)%7==0)
             index=4;
         else if((index-5)%7==0)
             index=5;
         else if((index-6)%7==0)
             index=6;
        switch (index) {
            case 0:
                return new SundayFragment();
            case 1:
                return new MondayFragment();
            case 2:
                return new TuesdayFragment();
            case 3:
                return new WednesdayFragment();
            case 4:
                return new ThursdayFragment();
            case 5:
                return new FridayFragment();
            case 6:
                return new SaturdayFragment();

        }

        return null;
    }


    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 147;
    }

}