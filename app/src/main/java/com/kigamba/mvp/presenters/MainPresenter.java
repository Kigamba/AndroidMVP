package com.kigamba.mvp.presenters;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface MainPresenter {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
