package com.newsman.newsman.auxiliary.sorting.news;

import com.newsman.newsman.server_entities.SimpleNews;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ByDateModified implements SimpleNewsSorting {
    @Override
    public void Sort(List<SimpleNews> array) {
        Collections.sort(array, (o1, o2) -> o1.getLastModified().compareTo(o2.getLastModified()));
    }
}
