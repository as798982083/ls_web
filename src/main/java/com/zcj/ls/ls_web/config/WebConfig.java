package com.zcj.ls.ls_web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system-params")
public class WebConfig {

    private String uploadPath;
    //养老平台接口Url地址
    private String ylLogin;
    private String ylGetServiceCategory;
    private String ylGetServiceItem;
    private String ylCommitOrder;
    private String ylQueryOrders;
    private String ylCancelOrder;
    //萤石平台接口url地址
    private String cameraAppKey;
    private String cameraAppSecret;
    private String cameraGetToken;
    private String cameraAddDevice;
    private String cameraDeviceNameUpdate;
    private String cameraDeviceEncryptOff;
    private String cameraLiveVideoOpen;
    private String cameraGetLiveAddress;
    private String cameraLiveVideoClose;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getYlLogin() {
        return ylLogin;
    }

    public void setYlLogin(String ylLogin) {
        this.ylLogin = ylLogin;
    }

    public String getYlGetServiceCategory() {
        return ylGetServiceCategory;
    }

    public void setYlGetServiceCategory(String ylGetServiceCategory) {
        this.ylGetServiceCategory = ylGetServiceCategory;
    }

    public String getYlGetServiceItem() {
        return ylGetServiceItem;
    }

    public void setYlGetServiceItem(String ylGetServiceItem) {
        this.ylGetServiceItem = ylGetServiceItem;
    }

    public String getYlCommitOrder() {
        return ylCommitOrder;
    }

    public void setYlCommitOrder(String ylCommitOrder) {
        this.ylCommitOrder = ylCommitOrder;
    }

    public String getYlQueryOrders() {
        return ylQueryOrders;
    }

    public void setYlQueryOrders(String ylQueryOrders) {
        this.ylQueryOrders = ylQueryOrders;
    }

    public String getYlCancelOrder() {
        return ylCancelOrder;
    }

    public void setYlCancelOrder(String ylCancelOrder) {
        this.ylCancelOrder = ylCancelOrder;
    }

    public String getCameraAppKey() {
        return cameraAppKey;
    }

    public void setCameraAppKey(String cameraAppKey) {
        this.cameraAppKey = cameraAppKey;
    }

    public String getCameraAppSecret() {
        return cameraAppSecret;
    }

    public void setCameraAppSecret(String cameraAppSecret) {
        this.cameraAppSecret = cameraAppSecret;
    }

    public String getCameraGetToken() {
        return cameraGetToken;
    }

    public void setCameraGetToken(String cameraGetToken) {
        this.cameraGetToken = cameraGetToken;
    }

    public String getCameraAddDevice() {
        return cameraAddDevice;
    }

    public void setCameraAddDevice(String cameraAddDevice) {
        this.cameraAddDevice = cameraAddDevice;
    }

    public String getCameraDeviceNameUpdate() {
        return cameraDeviceNameUpdate;
    }

    public void setCameraDeviceNameUpdate(String cameraDeviceNameUpdate) {
        this.cameraDeviceNameUpdate = cameraDeviceNameUpdate;
    }

    public String getCameraDeviceEncryptOff() {
        return cameraDeviceEncryptOff;
    }

    public void setCameraDeviceEncryptOff(String cameraDeviceEncryptOff) {
        this.cameraDeviceEncryptOff = cameraDeviceEncryptOff;
    }

    public String getCameraLiveVideoOpen() {
        return cameraLiveVideoOpen;
    }

    public void setCameraLiveVideoOpen(String cameraLiveVideoOpen) {
        this.cameraLiveVideoOpen = cameraLiveVideoOpen;
    }

    public String getCameraGetLiveAddress() {
        return cameraGetLiveAddress;
    }

    public void setCameraGetLiveAddress(String cameraGetLiveAddress) {
        this.cameraGetLiveAddress = cameraGetLiveAddress;
    }

    public String getCameraLiveVideoClose() {
        return cameraLiveVideoClose;
    }

    public void setCameraLiveVideoClose(String cameraLiveVideoClose) {
        this.cameraLiveVideoClose = cameraLiveVideoClose;
    }
}
