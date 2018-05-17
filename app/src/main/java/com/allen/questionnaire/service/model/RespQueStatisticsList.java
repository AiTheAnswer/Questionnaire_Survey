package com.allen.questionnaire.service.model;


import java.util.List;

/**
 * 问卷统计分析数据的集合
 *
 * @author Renjy
 */
public class RespQueStatisticsList extends Resp {


    /**
     * reason : null
     * object : {"statisticsList":[{"question":{"id":"2c9ba38162cea8230162ced128f50028","questionDes":"你认为本次培训对您有实际性的帮助么？","type":0,"optionIds":"113,114,115","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":113,"optionDes":"有"},"count":0},{"option":{"id":114,"optionDes":"不清楚"},"count":1},{"option":{"id":115,"optionDes":"没有"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced22d800029","questionDes":"你对讲师讲课的台风及讲课方式是否满意？","type":0,"optionIds":"116,117,118","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":116,"optionDes":"满意"},"count":1},{"option":{"id":117,"optionDes":"一般"},"count":0},{"option":{"id":118,"optionDes":"不满意"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced37905002a","questionDes":"你对讲师对课程重点的突破及讲解是否满意？","type":0,"optionIds":"119,120,121","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":119,"optionDes":"满意"},"count":0},{"option":{"id":120,"optionDes":"一般"},"count":1},{"option":{"id":121,"optionDes":"不满意"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced4a56d002b","questionDes":"你认为精英加强班时间如何安排更好？","type":0,"optionIds":"122,123,124","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":122,"optionDes":"两天一次"},"count":0},{"option":{"id":123,"optionDes":"一周一次"},"count":1},{"option":{"id":124,"optionDes":"其他"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced5dddc002c","questionDes":"你更希望学习那些方面的课程？","type":0,"optionIds":"125,126,127,128","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":125,"optionDes":"实战技能"},"count":0},{"option":{"id":126,"optionDes":"心态课程"},"count":1},{"option":{"id":127,"optionDes":"业务知识"},"count":0},{"option":{"id":128,"optionDes":"其他"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced778d9002d","questionDes":"你期待那种类型的培训方式？","type":0,"optionIds":"129,130,131,132,133","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":129,"optionDes":"现场教学"},"count":0},{"option":{"id":130,"optionDes":"视频授课"},"count":0},{"option":{"id":131,"optionDes":"学习交流会"},"count":1},{"option":{"id":132,"optionDes":"模拟演练"},"count":0},{"option":{"id":133,"optionDes":"其他"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced898d7002e","questionDes":"培训达到您设定的期望了么？","type":0,"optionIds":"134,135,136,137","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":134,"optionDes":"超出预期"},"count":0},{"option":{"id":135,"optionDes":"达到"},"count":1},{"option":{"id":136,"optionDes":"基本达到"},"count":0},{"option":{"id":137,"optionDes":"没有达到"},"count":0}]},{"question":{"id":"2c9ba38162cea8230162ced96d6e002f","questionDes":"本次培训的组织你满意么？","type":0,"optionIds":"138,139,140","questionnaireId":"2c9ba38162cea8230162ceaa27410002"},"optionStatistics":[{"option":{"id":138,"optionDes":"满意"},"count":0},{"option":{"id":139,"optionDes":"一般"},"count":1},{"option":{"id":140,"optionDes":"不满意"},"count":0}]}]}
     */

    private ObjectBean object;


    public ObjectBean getObject() {
        return object;
    }

    public void setObjectX(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        private List<QuestionnaireStatistics> statisticsList;

        public List<QuestionnaireStatistics> getStatisticsList() {
            return statisticsList;
        }

        public void setStatisticsList(List<QuestionnaireStatistics> statisticsList) {
            this.statisticsList = statisticsList;
        }

    }
}
