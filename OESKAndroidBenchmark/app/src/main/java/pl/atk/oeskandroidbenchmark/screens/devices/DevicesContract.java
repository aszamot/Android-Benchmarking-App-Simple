package pl.atk.oeskandroidbenchmark.screens.devices;

import junit.framework.Test;

import java.util.List;

import pl.atk.oeskandroidbenchmark.model.TestResult;

/**
 * Created by Tomasz on 20.01.2018.
 */

public interface DevicesContract {

    interface View {
        void stopRefreshAnimation();

        void startRefreshAnimation();

        void refreshData();

        void showNoConnectionSnackBar();

        void showErrorSnackBar();

        boolean checkInternetConnection();

        void adapterRefresh();
    }

    interface Presenter {
        List<TestResult> getDevicesList();

        void refreshDevicesList();
    }
}
