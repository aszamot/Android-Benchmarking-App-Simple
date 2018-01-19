package pl.atk.oeskandroidbenchmark.screens.devices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.atk.oeskandroidbenchmark.R;
import pl.atk.oeskandroidbenchmark.screens.benchmark.BenchmarkFragment;

/**
 * Created by Tomasz on 13.12.2017.
 */

public class DevicesFragment extends Fragment {

    Unbinder unbinder;

    public static DevicesFragment newInstance() {
        DevicesFragment devicesFragment = new DevicesFragment();

        return devicesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
