package pl.atk.oeskandroidbenchmark.screens.benchmark;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pl.atk.oeskandroidbenchmark.R;
import pl.atk.oeskandroidbenchmark.screens.main.MainActivity;
import pl.atk.oeskandroidbenchmark.ui.DeviceInfoCellView;
import pl.atk.oeskandroidbenchmark.utils.DeviceInfo;

/**
 * Created by Tomasz on 13.12.2017.
 */

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
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.test_button)
    Button testButton;

    @OnClick(R.id.test_button)
    public void makeTest() {
        presenter.preformTest(modelInfo.getAttributeValue());
    }

    Unbinder unbinder;

    SharedPreferences prefs;

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

        if (getActivity() != null) {
            prefs = getActivity().getSharedPreferences("BenchPrefs", Activity.MODE_PRIVATE);
            String scoreString = prefs.getString("LastScore", "???");
            setScore(scoreString);
        }

        presenter.onCreate();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setScore(String result) {
        score.setText(result);
    }

    @Override
    public void saveToPrefs(String score) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LastScore", score);
        editor.commit();
    }

    @Override
    public String getTestWord() {
        return getString(R.string.test_sentence);
    }

    @Override
    public void setProgressBarVisibility(boolean shouldBeVisible) {
        if (shouldBeVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTestButtonEnable(boolean shouldBeEnable) {
        testButton.setAlpha(shouldBeEnable ? 1 : 0.5f);
        testButton.setEnabled(shouldBeEnable);
    }

    @Override
    public void showNoConnectionSnackBar() {
        showSnackBar("No Internet connection. Score can't be saved.");
    }

    @Override
    public void showErrorSnackBar() {
        showSnackBar("Error while saving score");
    }

    @Override
    public boolean checkInternetConnection() {
        return ((MainActivity) getActivity()).checkInternetConnection();
    }

    private void showSnackBar(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_SHORT).show();
    }
}
