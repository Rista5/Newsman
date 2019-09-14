package com.newsman.newsman.auxiliary.sorting.news;

import com.newsman.newsman.model.db_entities.SimpleNews;

import java.util.List;

public interface SimpleNewsSorting {
    void sort(List<SimpleNews> list);
}
