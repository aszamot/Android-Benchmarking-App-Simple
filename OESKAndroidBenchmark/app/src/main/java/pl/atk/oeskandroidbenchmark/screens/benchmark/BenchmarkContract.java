package pl.atk.oeskandroidbenchmark.screens.benchmark;

/**
 * Created by Tomasz on 13.12.2017.
 */

public interface BenchmarkContract {

    interface View{
        String getTestWord();
        void setProgressBarVisibility(boolean shouldBeVisible);
        void setScore(String result);
        void setTestButtonEnable(boolean shouldBeEnable);
    }

    interface Presenter{
        void preformTest();
    }
}
