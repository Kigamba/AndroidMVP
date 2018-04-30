package com.kigamba.mvp.communication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class BroadcastManager {

    public static final String NEW_NOTE_EVENT = "NEW NOTE EVENT";

    public static void sendNewNoteEvent(Context context) {
        Intent intent = new Intent();
        intent.setAction(NEW_NOTE_EVENT);

        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(intent);
    }

}