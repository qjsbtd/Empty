package com.black.vision.mode.net;

/**
 * Description: Interface net operation result callback
 * Date：19-1-8-上午11:15
 * Author: black
 */
public interface INetCallback {
    void onSuccess(String result);
    void onFailure(int failCode, String result);
}
