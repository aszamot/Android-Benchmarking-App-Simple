package pl.atk.oeskandroidbenchmark.screens.devices;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import junit.framework.Test;

import java.util.LinkedList;
import java.util.List;

import pl.atk.oeskandroidbenchmark.model.TestResult;

/**
 * Created by Tomasz on 20.01.2018.
 */

public class DevicesPresenter implements DevicesContract.Presenter {

    private DevicesContract.View view;

    private List<TestResult> devicesList = new LinkedList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DevicesPresenter(DevicesContract.View view) {
        this.view = view;
    }

    @Override
    public List<TestResult> getDevicesList() {
        return devicesList;
    }

    @Override
    public void refreshDevicesList() {
        fetchDeviceList();
    }

    private void fetchDeviceList() {
        if (view.checkInternetConnection()) {
            devicesList.clear();
            db.collection("models")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    view.startRefreshAnimation();
                                    final TestResult testResult = new TestResult();
                                    testResult.setDeviceName(document.getId());
                                    db.collection("models/" + document.getId() + "/results")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        Double i = 0d;
                                                        Double sum = 0d;
                                                        for (DocumentSnapshot document : task.getResult()) {
                                                            i++;
                                                            sum += ((Long) document.getData().get("scoreFullTime")).doubleValue();
                                                        }
                                                        Double avgFullTime = sum / i;
                                                        Long avgFullTimeLong = Math.round(avgFullTime);
                                                        Double tempResult = avgFullTime / 10000d;
                                                        Long avgShortTimeLong = Math.round(tempResult);
                                                        testResult.setShortTime(avgShortTimeLong);
                                                        testResult.setFullTime(avgFullTimeLong);
                                                        devicesList.add(testResult);
                                                        view.stopRefreshAnimation();
                                                        view.adapterRefresh();
                                                    } else {
                                                        view.showErrorSnackBar();
                                                    }
                                                }
                                            });
                                }
                            } else {
                                view.showErrorSnackBar();
                            }
                        }
                    });
        } else {
            view.stopRefreshAnimation();
            view.showNoConnectionSnackBar();
        }
    }
}
