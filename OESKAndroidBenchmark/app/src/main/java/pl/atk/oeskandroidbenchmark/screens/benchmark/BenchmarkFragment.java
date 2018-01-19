package pl.atk.oeskandroidbenchmark.screens.benchmark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pl.atk.oeskandroidbenchmark.R;
import pl.atk.oeskandroidbenchmark.ui.DeviceInfoCellView;
import pl.atk.oeskandroidbenchmark.utils.DeviceInfo;

/**
 * Created by Tomasz on 13.12.2017.
 */

//todo after score click display popup with details
public class BenchmarkFragment extends Fragment implements BenchmarkContract.View {

    private BenchmarkContract.Presenter presenter = new BenchmarkPresenter(this);

    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.model_info)
    DeviceInfoCellView modelInfo;
    @BindView(R.id.os_info)
    DeviceInfoCellView osInfo;
    @BindView(R.id.build_info)
    DeviceInfoCellView buildInfo;
    @BindView(R.id.cpu_info)
    DeviceInfoCellView cpuInfo;
    @BindView(R.id.cores_info)
    DeviceInfoCellView coresInfo;
    @BindView(R.id.memory_info)
    DeviceInfoCellView memoryInfo;
    @BindView(R.id.motherboard_info)
    DeviceInfoCellView motherboardInfo;

    @OnClick(R.id.test_button)
    public void makeTest() {
        //todo perform test on click put score (for now total time)
    }

    Unbinder unbinder;

    public static BenchmarkFragment newInstance() {
        BenchmarkFragment benchmarkFragment = new BenchmarkFragment();

        return benchmarkFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_benchmark, container, false);
        unbinder = ButterKnife.bind(this, view);

        modelInfo.setAttributeValue(DeviceInfo.getDeviceModel());
        osInfo.setAttributeValue(DeviceInfo.getDeviceOS());
        buildInfo.setAttributeValue(DeviceInfo.getDeviceOsBuild());
        cpuInfo.setAttributeValue(DeviceInfo.getDeviceCpu());
        coresInfo.setAttributeValue(DeviceInfo.getDeviceCpuCores());
        motherboardInfo.setAttributeValue(DeviceInfo.getDeviceMotherBoard());
        memoryInfo.setAttributeValue(DeviceInfo.getDeviceTotalRAM(getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public String getTestWord() {
        return getString(R.string.test_sentence);
    }
}
