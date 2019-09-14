package com.newsman.newsman.auxiliary.sorting.news;

import com.newsman.newsman.model.db_entities.SimpleNews;

import java.util.Collections;
import java.util.List;

public class ByTitle implements SimpleNewsSorting {
    @Override
    public void sort(List<SimpleNews> list) {
        Collections.sort(list, (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
    }
}
