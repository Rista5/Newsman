package com.newsman.newsman.REST;

import java.util.ArrayList;
import java.util.List;

public class RunnableComposite implements Runnable {

    private List<Runnable> componentList;

    public RunnableComposite() {
        componentList = new ArrayList<>();
    }

    public void add(Runnable runnable) {
        componentList.add(runnable);
    }

    public void remove(Runnable runnable) {
        componentList.remove(runnable);
    }

    @Override
    public void run() {
        for(Runnable runnable: componentList) {
            runnable.run();
        }
    }
}
