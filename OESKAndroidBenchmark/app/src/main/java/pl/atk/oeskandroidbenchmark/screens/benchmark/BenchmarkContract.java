package pl.atk.oeskandroidbenchmark.screens.benchmark;

/**
 * Created by Tomasz on 13.12.2017.
 */

public interface BenchmarkContract {

    interface View{
        String getTestWord();
    }

    interface Presenter{
        void performSHATest();
        void performMD5Test();
    }
}
