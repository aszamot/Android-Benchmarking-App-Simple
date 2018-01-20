package pl.atk.oeskandroidbenchmark.screens.benchmark;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.EventListener;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Tomasz on 13.12.2017.
 */


public class BenchmarkPresenter implements BenchmarkContract.Presenter {

    private BenchmarkContract.View view;

    private final int HOW_MANY_TEST_REPEATS = 20000;
    private final int CODE_TO_BROKE = 9999999;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BenchmarkPresenter(BenchmarkContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void preformTest(String deviceName) {
        TestTask testTask = new TestTask();
        testTask.execute(deviceName);
    }

    private void performSHATest() {
        for (int i = 0; i < HOW_MANY_TEST_REPEATS; i++) {
            performSHA(view.getTestWord());
        }
    }

    private void performMD5Test() {
        for (int i = 0; i < HOW_MANY_TEST_REPEATS; i++) {
            performMD5(view.getTestWord());
        }
    }

    private void performBruteForce() {
        Integer successfulCode = 0;
        for (Integer i = 0; i < CODE_TO_BROKE; i++) {
            successfulCode = i;
        }
    }

    private void performMD5(String word) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(word.getBytes());

            byte[] data = messageDigest.digest();
            StringBuffer md5Hash = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                String h = Integer.toHexString(0xFF & data[i]);
                while (h.length() < 2)
                    h = "0" + h;
                md5Hash.append(h);
            }

            String hashValue = md5Hash.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void performSHA(String word) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(word.getBytes("ASCII"));

            byte[] data = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            String hex = Base64.encodeToString(data, 0, data.length, 0);
            stringBuffer.append(hex);

            String hashValue = stringBuffer.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    class TestTask extends AsyncTask<String, Void, Long> {

        @Override
        protected void onPreExecute() {
            view.setScore("???");
            view.setProgressBarVisibility(true);
            view.setTestButtonEnable(false);
        }

        @Override
        protected Long doInBackground(String... name) {
            final String devicename = name[0];

            Long hashTimeStart = System.nanoTime();
            performSHATest();
            Long hashTimeTotal = System.nanoTime() - hashTimeStart;

            Long md5TimeStart = System.nanoTime();
            performMD5Test();
            Long md5TimeTotal = System.nanoTime() - md5TimeStart;

            Long bruteForceTimeStart = System.nanoTime();
            performBruteForce();
            Long bruteForceTimeTotal = System.nanoTime() - bruteForceTimeStart;

            final Long fullTestResult = hashTimeTotal + md5TimeTotal + bruteForceTimeTotal;
            Double tempResult = fullTestResult / 10000d;
            final Long shortTestResult = Math.round(tempResult);

            if (view.checkInternetConnection()) {
                Map<String, Object> dataToSave = new HashMap<>();
                dataToSave.put("deviceName", devicename);

                final Map<String, Object> resultToSave = new HashMap<>();
                resultToSave.put("scoreFullTime", fullTestResult);

                db.collection("models").document(devicename)
                        .set(dataToSave)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.collection("models/" + devicename + "/results")
                                        .add(resultToSave)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                view.saveToPrefs(String.valueOf(shortTestResult));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                view.showErrorSnackBar();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                view.showErrorSnackBar();
                            }
                        });
            } else {
                view.showNoConnectionSnackBar();
            }

            return shortTestResult;
        }

        @Override
        protected void onPostExecute(Long shortResult) {
            view.setScore(shortResult.toString());
            view.setProgressBarVisibility(false);
            view.setTestButtonEnable(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            view.setProgressBarVisibility(false);
        }
    }
}
