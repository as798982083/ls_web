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
}
