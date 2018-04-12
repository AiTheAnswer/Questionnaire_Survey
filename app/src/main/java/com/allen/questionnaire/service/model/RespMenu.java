package com.allen.questionnaire.service.model;

import java.io.Serializable;

/**
 * Created by Allen on 2018/3/13.
 */

public class RespMenu extends Resp implements Serializable {
    /**
     * "menuName":"排行榜",
     * "menuId":"60005",
     * "orderId":"1",
     * "menuIcon":"list_menu_rangking",
     * "enable":1,
     * "isWaterMark":0,
     * "isBuy":0,
     * "menuStyle":"MS001",
     * "focus":"00000"
     */

    private String menuName;
    private String menuId;
    private String orderId;
    private String menuIcon;
    private int enable;
    private int isWaterMark;
    private int isBuy;
    private int isPower;
    private String menuStyle;
    private String focus;
    private int isVisible;

    public RespMenu() {
    }

    public RespMenu(String menuName, String menuId, String orderId, String menuIcon) {
        this.menuName = menuName;
        this.menuId = menuId;
        this.orderId = orderId;
        this.menuIcon = menuIcon;
    }

    public RespMenu(String menuName, String menuId, String orderId, String menuIcon, int enable, int isWaterMark, int isBuy, int isPower, String menuStyle, String focus, int isVisible) {
        this.menuName = menuName;
        this.menuId = menuId;
        this.orderId = orderId;
        this.menuIcon = menuIcon;
        this.enable = enable;
        this.isWaterMark = isWaterMark;
        this.isBuy = isBuy;
        this.isPower = isPower;
        this.menuStyle = menuStyle;
        this.focus = focus;
        this.isVisible = isVisible;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getIsWaterMark() {
        return isWaterMark;
    }

    public void setIsWaterMark(int isWaterMark) {
        this.isWaterMark = isWaterMark;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }

    public String getMenuStyle() {
        return menuStyle;
    }

    public void setMenuStyle(String menuStyle) {
        this.menuStyle = menuStyle;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public int getIsPower() {
        return isPower;
    }

    public void setIsPower(int isPower) {
        this.isPower = isPower;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public String toString() {
        return "RespMenu{" +
                "menuName='" + menuName + '\'' +
                ", menuId='" + menuId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", enable=" + enable +
                ", isWaterMark=" + isWaterMark +
                ", isBuy=" + isBuy +
                ", isPower=" + isPower +
                ", menuStyle='" + menuStyle + '\'' +
                ", focus='" + focus + '\'' +
                ", isVisible=" + isVisible +
                '}';
    }
}
