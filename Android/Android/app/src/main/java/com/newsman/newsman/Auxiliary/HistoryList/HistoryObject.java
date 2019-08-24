package com.newsman.newsman.Auxiliary.HistoryList;

public class HistoryObject<T> {
    private OperationType operation;

    private T object;

    public HistoryObject(OperationType operation, T object){
        this.operation = operation;
        this.object = object;
    }

    public OperationType getOp() {
        return operation;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}

