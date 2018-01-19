package pl.atk.oeskandroidbenchmark.screens.benchmark;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tomasz on 13.12.2017.
 */


//todo bruteforce
//todo convert test to separate thread - USING RXJAVA2
public class BenchmarkPresenter implements BenchmarkContract.Presenter {

    private BenchmarkContract.View view;

    private final int HOW_MANY_TEST_REPEATS = 20000;

    public BenchmarkPresenter(BenchmarkContract.View view) {
        this.view = view;
    }

    @Override
    public void performSHATest() {
        for (int i = 0; i < HOW_MANY_TEST_REPEATS; i++) {
            performSHA(view.getTestWord());
        }
    }

    @Override
    public void performMD5Test() {
        for (int i = 0; i < HOW_MANY_TEST_REPEATS; i++) {
            performMD5(view.getTestWord());
        }
    }

    private void performMD5(String word) {
        //todo ogarnąć jak to działa
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
        //todo ogarnąć jak to działa
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

}
