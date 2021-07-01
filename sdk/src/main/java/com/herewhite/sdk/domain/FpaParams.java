package com.herewhite.sdk.domain;

/**
 * 全链路加速参数
 */
public class FpaParams extends WhiteObject {
    /**
     * 产品标识
     */
    String appId;
    /**
     * 链路标识
     */
    String token;
    /**
     * 链路标识
     */
    int chainId;

    public FpaParams(String appId, String token, int chainId) {
        this.appId = appId;
        this.token = token;
        this.chainId = chainId;
    }

    public String getAppId() {
        return appId;
    }

    public String getToken() {
        return token;
    }

    public int getChainId() {
        return chainId;
    }
}
