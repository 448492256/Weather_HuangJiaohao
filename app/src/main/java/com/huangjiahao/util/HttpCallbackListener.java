package com.huangjiahao.util;

/**
 * 用于回调子线程返回数据的接口
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
