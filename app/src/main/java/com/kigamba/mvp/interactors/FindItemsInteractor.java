package com.kigamba.mvp.interactors;

import java.util.List;
/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface FindItemsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);
}
