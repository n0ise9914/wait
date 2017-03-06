package com.n0ize.wait;

import com.n0ize.wait.config.WaitConfiguration;
import com.n0ize.wait.events.OnConnectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * n0ise on 3/4/2017.
 */
public class Wait {
    private static WaitConfiguration waitConfiguration;
    private static List<OnConnectedListener> onConnectedListeners;

    static List<OnConnectedListener> getOnConnectedListeners() {
        return onConnectedListeners;
    }

    public static void addOnConnectedListeners(OnConnectedListener onConnectedListener) {
        if (onConnectedListeners == null) onConnectedListeners = new ArrayList<>();
        onConnectedListeners.add(onConnectedListener);
    }

    public static void refresh() {
        if (onConnectedListeners == null) onConnectedListeners = new ArrayList<>();
        onConnectedListeners.clear();
    }

    public static void setConfiguration(WaitConfiguration waitConfiguration) {
        Wait.waitConfiguration = waitConfiguration;
    }

    static WaitConfiguration getConfiguration() {
        if (waitConfiguration == null)
            return new WaitConfiguration.Builder().build();
        else
            return waitConfiguration;
    }
}
