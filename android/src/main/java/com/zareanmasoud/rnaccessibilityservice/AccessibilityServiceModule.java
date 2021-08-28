package com.zareanmasoud.rnaccessibilityservice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityEvent;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityService;
import android.provider.Settings;
import android.content.pm.ServiceInfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Promise;

import java.util.List;
import javax.annotation.Nullable;

public class AccessibilityServiceModule extends ReactContextBaseJavaModule {

    // TODO: should be non-static and final
    private static ReactApplicationContext reactContext;

    public AccessibilityServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AccessibilityService";
    }

    @ReactMethod
    public void navigateToAccessibilitySettings() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        // startActivityForResult never worked for me...
        reactContext.getCurrentActivity().startActivity(intent);
    }

    @ReactMethod
    public void checkAccessibilitySettings(Promise promise) {
        promise.resolve(
                AccessibilityServiceModule.isAccessibilityServiceEnabled(reactContext, MyAccessibilityService.class));
    }

    // TODO: should be non-static
    private static void sendEvent(ReactContext reactContext, String eventName, @Nullable String params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    // TODO: should be non-static
    public static void prepareEvent(int keyCode) {
        WritableMap params = Arguments.createMap();
        params.putInt("keyCode", keyCode);

        sendEvent(reactContext, "BackgroundKeyPress", params);
    }

    private static boolean isAccessibilityServiceEnabled(Context context,
            Class<? extends AccessibilityService> service) {
        // ActivityManager queries never worked for me...
        String settingValue = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        return settingValue != null && settingValue.contains(context.getPackageName() + "/" + service.getName());
    }
}
