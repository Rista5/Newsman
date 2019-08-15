package com.newsman.newsman.REST.Put;

import java.util.ArrayList;
import java.util.List;

public class SendComposite implements SendComponent {

    private List<SendComponent> componentList;

    public SendComposite() {
        componentList = new ArrayList<>();
    }

    public void add(SendComponent sendComponent) {
        componentList.add(sendComponent);
    }

    public void remove(SendComponent sendComponent) {
        componentList.remove(sendComponent);
    }

    @Override
    public void sendRequest() {
        for(SendComponent sendComponent: componentList) {
            sendComponent.sendRequest();
        }
    }
}
