package com.goucorporation.android.orientateuni.presentation.presenters;

import com.goucorporation.android.orientateuni.presentation.views.views.BaseView;

public class BasePresenter<V extends BaseView> {

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }
}
