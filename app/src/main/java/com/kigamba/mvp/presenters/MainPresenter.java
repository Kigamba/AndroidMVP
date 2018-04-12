package com.kigamba.mvp.presenters;

public interface MainPresenter {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
