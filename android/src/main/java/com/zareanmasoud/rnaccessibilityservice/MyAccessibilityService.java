package com.zareanmasoud.rnaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.KeyEvent;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onInterrupt() {
        // PASS
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();

        //handle keyevent for widnows key
        AccessibilityServiceModule.prepareEvent(keyCode);
        return super.onKeyEvent(event);
    }
}
