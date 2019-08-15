package com.newsman.newsman.ServerEntities;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HistoryList<T> implements List<T>, History<T> {

    private List<T> list;
    private List<HistoryObject<T>> history;

    public HistoryList(List<T> list) {
        this.list = list;
        history = new ArrayList<>();
    }

    @Override
    public void insertedElement(T e) {
        history.add(new HistoryObject<T>(OperationType.INSERT, e));
    }

    @Override
    public void updatedElement(T oldE, T newE) {
        boolean found = false;
        for(HistoryObject h: history)
            if (h.getObject().equals(oldE)) {
                h.setObject(newE);
                found = true;
            }
        if(!found){
            history.add(new HistoryObject<T>(OperationType.UPDATE, newE));
        }
    }

    @Override
    public void deletedElement(T e) {
        boolean found = false;
        Iterator<HistoryObject<T>> i = history.listIterator();
        while(i.hasNext() && !found) {
            HistoryObject o = i.next();
            if(o.getObject().equals(e)){
                i.remove();
                found = true;
            }
        }
        if(!found){
            history.add(new HistoryObject<T>(OperationType.DELETE, e));
        }
    }

    @Override
    public void setList(List<T> list) {
        this.list = list;
        history = new ArrayList<>();
    }

    @Override
    public List<HistoryObject<T>> getHistory() {
        return history;
    }

    // changed methods
    @Override
    public boolean add(T o) {
        boolean res = list.add(o);
        if(res)
            insertedElement(o);
        return res;
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
        insertedElement(element);
    }

    @Override
    public boolean addAll(Collection<? extends T>  c) {
        return list.addAll(c);
//        boolean res = list.addAll(c);
//        if(res)
//            for(T e: c){
//                insertedElement(e);
//            }
//        return res;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
//        boolean res =  list.addAll(index, c);
//        if(res)
//            for(T e: c){
//                insertedElement(e);
//            }
//        return res;
    }

    @Override
    public boolean remove(Object o) {
        T e = list.get(list.indexOf(o));
        boolean res =  list.remove(o);
        if(res)
            deletedElement(e);
        return res;
    }

    @Override
    public T remove(int index) {
        T res = list.remove(index);
        if(res !=null)
            deletedElement(res);
        return res;
    }

    @Override
    public boolean removeAll(Collection c) {
        return list.removeAll(c);
//        boolean res = list.removeAll(c);
//        if(res)
//            for(Object o: c) {
//                T e = (T) o;
//                if(e != null){
//                    deletedElement(e);
//                }
//            }
//        return res;
    }

    @Override
    public T set(int index, T element) {
        T res = list.set(index, element);
//        if(res != null)
//            deletedElement(res);
        updatedElement(res, element);
        return res;
    }

    @Override
    public void clear() {
        list.clear();
        history.clear();
    }

    //changed methods end

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return list.toArray();
    }

}
