package pl.atk.oeskandroidbenchmark.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tomasz on 13.12.2017.
 */

public class DeviceInfo {

    public static String getDeviceModel() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static String getDeviceOS() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String getDeviceTotalRAM(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        return Long.toString(memoryInfo.totalMem / 1048576L) + " MB";
    }

    public static String getDeviceMotherBoard() {
        return Build.BOARD;
    }

    public static String getDeviceOsBuild() {
        return Build.VERSION.INCREMENTAL;
    }

    public static String getDeviceCpu() {
        return Build.CPU_ABI;
    }

    public static String getDeviceCpuCores() {
        return Integer.toString(Runtime.getRuntime().availableProcessors());
    }
}
