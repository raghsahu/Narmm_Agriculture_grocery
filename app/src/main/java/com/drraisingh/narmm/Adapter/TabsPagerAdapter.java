package com.drraisingh.narmm.Adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.drraisingh.narmm.Fragment.DetaillFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new DetaillFragment();
            case 1:
                // Games fragment activity
              /*  return new FeatursFragment();*/
            case 2:
                // Movies fragment activity
             /*   return new DosecFragment();*/
            case 3:
                // Movies fragment activity
              /*  return new InsectFragment();*/
            case 4:
                // Movies fragment activity
               /* return new NonelFragment();*/
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}
