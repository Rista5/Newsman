package com.newsman.newsman.auxiliary.sorting.news;

import com.newsman.newsman.server_entities.SimpleNews;

import java.util.List;

public interface SimpleNewsSorting {
    void sort(List<SimpleNews> list);
}
