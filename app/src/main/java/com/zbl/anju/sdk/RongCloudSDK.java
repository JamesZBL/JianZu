package com.zbl.anju.sdk;

/**
 * @author James
 * @描述 RongIMClient提供的SDK
 */

public class RongCloudSDK {

    private static RongCloudSDK mInstance;

    private RongCloudSDK() {
    }

    public static RongCloudSDK getInstance() {
        if (mInstance == null) {
            synchronized (RongCloudSDK.class) {
                if (mInstance == null) {
                    mInstance = new RongCloudSDK();
                }
            }
        }
        return mInstance;
    }
}
