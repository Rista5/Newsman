package com.newsman.newsman.Auxiliary.HistoryList;

import java.util.List;

public interface History<T> {
    void insertedElement(T e);
    void updatedElement(T oldE, T newE);
    void deletedElement(T e);
    void setList(List<T> list);
    List<HistoryObject<T>> getHistory();
}


