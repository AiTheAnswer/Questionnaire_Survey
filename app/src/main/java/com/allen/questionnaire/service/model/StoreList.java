package com.allen.questionnaire.service.model;


import com.allen.questionnaire.service.datatrasfer.WinnerResponse;

import java.util.List;

/**
 * Created by Allen on 2018/1/30.
 */

public class StoreList implements WinnerResponse {

    /**
     * siteList : [{"siteKey":"P00001","siteName":"摩登总店","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00002","siteName":"花地湾店","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00003","siteName":"店铺1","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00004","siteName":"店铺2","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00005","siteName":"店铺3","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00006","siteName":"店铺4","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00007","siteName":"店铺5","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00008","siteName":"店铺6","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00009","siteName":"店铺7","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00010","siteName":"店铺8","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00011","siteName":"店铺9","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00012","siteName":"店铺10","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00013","siteName":"店铺11","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00014","siteName":"店铺12","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00015","siteName":"店铺13","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00016","siteName":"店铺14","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00017","siteName":"店铺15","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00018","siteName":"店铺16","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00019","siteName":"店铺17","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00020","siteName":"店铺18","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00021","siteName":"店铺19","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00022","siteName":"店铺20","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00023","siteName":"店铺21","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00024","siteName":"店铺22","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00025","siteName":"店铺23","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00026","siteName":"店铺24","siteType":"300","isTodayInspected":"false"},{"siteKey":"P00027","siteName":"店铺25","siteType":"300","isTodayInspected":"false"}]
     * status : success
     * reason : null
     */

    private String status;
    private Object reason;
    private List<SiteListBean> siteList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public List<SiteListBean> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<SiteListBean> siteList) {
        this.siteList = siteList;
    }

    public static class SiteListBean {
        /**
         * siteKey : P00001
         * siteName : 摩登总店
         * siteType : 300
         * isTodayInspected : false
         */

        private String siteKey;
        private String siteName;
        private String siteType;
        private String isTodayInspected;

        public String getSiteKey() {
            return siteKey;
        }

        public void setSiteKey(String siteKey) {
            this.siteKey = siteKey;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getSiteType() {
            return siteType;
        }

        public void setSiteType(String siteType) {
            this.siteType = siteType;
        }

        public String getIsTodayInspected() {
            return isTodayInspected;
        }

        public void setIsTodayInspected(String isTodayInspected) {
            this.isTodayInspected = isTodayInspected;
        }

        @Override
        public String toString() {
            return "siteKey=" + siteKey + "," +
                    "siteName=" + siteName + "," +
                    "siteType=" + siteType + "," +
                    "isTodayInspected=" + isTodayInspected;
        }
    }
}
