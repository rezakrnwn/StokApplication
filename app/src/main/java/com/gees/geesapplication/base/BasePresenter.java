package com.gees.geesapplication.base;

/**
 * Created by SERVER on 11/09/2017.
 */

public interface BasePresenter<V> {

    void attachView(V view);
    void detachView();
    boolean isViewAttached();
}
