package com.zareanmasoud.rnaccessibilityservice;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

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
        reactContext.startActivity(intent);
    }

    @ReactMethod
    public void checkAccessibilitySettings() {
        // TODO: Promise()
        return isAccessibilityServiceEnabled();
    }

    // TODO: should be non-static
    private static void sendEvent(
            ReactContext reactContext,
            String eventName,
            @Nullable String params
    ) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    // TODO: should be non-static
    public static void prepareEvent(String params) {
        sendEvent(reactContext, "EventReminder", params);
    }

    private static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(service.getName())) {
                {
                    return true;
                }
            }
        }

        return false;
    }
}
