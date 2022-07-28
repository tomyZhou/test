package com.example.event;

import android.util.Log;

import java.util.HashMap;

public class EventTrack {
    public static void track(HashMap<String, String> params) {
        Log.e("EventTrack", "我擦，开始打点-->");
    }
}
