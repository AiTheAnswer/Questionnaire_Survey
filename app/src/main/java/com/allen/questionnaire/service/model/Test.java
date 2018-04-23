package com.allen.questionnaire.service.model;

import java.util.List;

/**
 * Created by allen on 2018-4-19.
 */

public class Test extends Resp {

    private List<QuesDetailBean> quesDetail;

    public List<QuesDetailBean> getQuesDetail() {
        return quesDetail;
    }

    public void setQuesDetail(List<QuesDetailBean> quesDetail) {
        this.quesDetail = quesDetail;
    }

    public static class QuesDetailBean {
        /**
         * question : {"id":"4028308262a968110162a96830550000","questionDes":"你认为什么时候应该确立择业观","type":0,"optionIds":"1,2,3,4,68","questionnaireId":"2c9ba38162c48f4d0162c48f8b9d0000"}
         * optionList : [{"id":1,"optionDes":"大二"},{"id":2,"optionDes":"大三"},{"id":3,"optionDes":"大四"},{"id":4,"optionDes":"毕业后"},{"id":68,"optionDes":"大一"}]
         */

        private QuestionBean question;
        private List<OptionListBean> optionList;

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public List<OptionListBean> getOptionList() {
            return optionList;
        }

        public void setOptionList(List<OptionListBean> optionList) {
            this.optionList = optionList;
        }

        public static class QuestionBean {
            /**
             * id : 4028308262a968110162a96830550000
             * questionDes : 你认为什么时候应该确立择业观
             * type : 0
             * optionIds : 1,2,3,4,68
             * questionnaireId : 2c9ba38162c48f4d0162c48f8b9d0000
             */

            private String id;
            private String questionDes;
            private int type;
            private String optionIds;
            private String questionnaireId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQuestionDes() {
                return questionDes;
            }

            public void setQuestionDes(String questionDes) {
                this.questionDes = questionDes;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOptionIds() {
                return optionIds;
            }

            public void setOptionIds(String optionIds) {
                this.optionIds = optionIds;
            }

            public String getQuestionnaireId() {
                return questionnaireId;
            }

            public void setQuestionnaireId(String questionnaireId) {
                this.questionnaireId = questionnaireId;
            }
        }

        public static class OptionListBean {
            /**
             * id : 1
             * optionDes : 大二
             */

            private int id;
            private String optionDes;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOptionDes() {
                return optionDes;
            }

            public void setOptionDes(String optionDes) {
                this.optionDes = optionDes;
            }
        }
    }
}
