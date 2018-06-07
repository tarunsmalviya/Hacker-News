package com.tarunsmalviya.hackernews.util;

public enum ListType {
    NEW_STORIES("New Stories"), TOP_STORIES("Top Stories"), BEST_STORIES("Best Stories");

    private String name;

    ListType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
