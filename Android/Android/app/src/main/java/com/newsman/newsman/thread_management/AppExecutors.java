package com.newsman.newsman.thread_management;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final int NUMBER_OF_NETWORK_THREADS = 1;
    private static final Object LOCK = new Object();
    private static AppExecutors mInstance;
    private final Executor networkIO;
    private final Executor databaseIO;

    private AppExecutors(Executor networkIO, Executor databaseIO) {
        this.networkIO = networkIO;
        this.databaseIO = databaseIO;
    }

    public static AppExecutors getInstance() {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = new AppExecutors( Executors.newFixedThreadPool(NUMBER_OF_NETWORK_THREADS),
                        Executors.newSingleThreadExecutor());

            }
        }
        return mInstance;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getDatabaseIO() {
        return databaseIO;
    }

}
