package pl.atk.oeskandroidbenchmark.model;

/**
 * Created by Tomasz on 19.01.2018.
 */

public class TestResult {

    private String deviceName;
    private Long fullTime;
    private Long shortTime;

    public Long getFullTime() {
        return fullTime;
    }

    public void setFullTime(Long fullTime) {
        this.fullTime = fullTime;
    }

    public Long getShortTime() {
        return shortTime;
    }

    public void setShortTime(Long shortTime) {
        this.shortTime = shortTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
