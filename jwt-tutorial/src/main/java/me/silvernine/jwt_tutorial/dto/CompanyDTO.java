package me.silvernine.jwt_tutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;


import java.util.List;

@Data
@Getter
public class CompanyDTO {
    @JsonProperty("result")
    private Result result;

    // Getters and setters
    @Data
    public static class Result {
        @JsonProperty("prdt_div")
        private String prdtDiv;

        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("max_page_no")
        private int maxPageNo;

        @JsonProperty("now_page_no")
        private int nowPageNo;

        @JsonProperty("err_cd")
        private String errCd;

        @JsonProperty("err_msg")
        private String errMsg;

        @JsonProperty("baseList")
        private List<Base> baseList;

        @JsonProperty("optionList")
        private List<Option> optionList;

        // Getters and setters
        @Data
        public static class Base {
            @JsonProperty("dcls_month")
            private String dclsMonth;

            @JsonProperty("fin_co_no")
            private String finCoNo;

            @JsonProperty("kor_co_nm")
            private String korCoNm;

            @JsonProperty("dcls_chrg_man")
            private String dclsChrgMan;

            @JsonProperty("homp_url")
            private String hompUrl;

            @JsonProperty("cal_tel")
            private String calTel;
        }
        @Data
        public static class Option {
            @JsonProperty("dcls_month")
            private String dclsMonth;

            @JsonProperty("fin_co_no")
            private String finCoNo;

            @JsonProperty("area_cd")
            private String areaCd;

            @JsonProperty("area_nm")
            private String areaNm;

            @JsonProperty("exis_yn")
            private String exisYn;
        }
    }
}