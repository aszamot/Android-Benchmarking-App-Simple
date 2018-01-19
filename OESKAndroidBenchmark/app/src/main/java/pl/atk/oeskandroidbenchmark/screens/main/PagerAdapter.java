package pl.atk.oeskandroidbenchmark.screens.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.atk.oeskandroidbenchmark.screens.benchmark.BenchmarkFragment;
import pl.atk.oeskandroidbenchmark.screens.devices.DevicesFragment;

/**
 * Created by Tomasz on 13.12.2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int fragmentCount;

    public  PagerAdapter(FragmentManager fm, int fragmentCount){
        super(fm);
        this.fragmentCount = fragmentCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return BenchmarkFragment.newInstance();
            case 1:
                return DevicesFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragmentCount;
    }
}
