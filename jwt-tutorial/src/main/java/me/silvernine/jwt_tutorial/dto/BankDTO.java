package me.silvernine.jwt_tutorial.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class BankDTO {
    @JsonProperty("result")
    private Result result;


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


        @Data
        public static class Base {
            @JsonProperty("dcls_month")
            private String dclsMonth;

            @JsonProperty("fin_co_no")
            private String finCoNo;

            @JsonProperty("kor_co_nm")
            private String korCoNm;

            @JsonProperty("fin_prdt_nm")
            private String finPrdtNm;

            @JsonProperty("join_way")
            private String joinWay;

            @JsonProperty("mtrt_int")
            private String mtrtInt;

            @JsonProperty("spcl_cnd")
            private String spclCnd;

            @JsonProperty("join_deny")
            private String joinDeny;

            @JsonProperty("join_member")
            private String joinMember;

            @JsonProperty("etc_note")
            private String etcNote;

            @JsonProperty("max_limit")
            private String maxLimit;

            @JsonProperty("loan_inci_expn")
            private String loanInciExpn;

            @JsonProperty("erly_rpay_fee")
            private String erlyRpayFee;

            @JsonProperty("dly_rate")
            private String dlyRate;

            @JsonProperty("loan_lmt")
            private String loanLmt;

            @JsonProperty("dcls_strt_day")
            private String dclsStrtDay;

            @JsonProperty("dcls_end_day")
            private String dclsEndDay;

            @JsonProperty("fin_co_subm_day")
            private String finCoSubmDay;
        }

        @Data
        public static class Option {

            @JsonProperty("dcls_month")
            private String dclsMonth;

            @JsonProperty("fin_co_no")
            private String finCoNo;

            @JsonProperty("intr_rate_type")
            private String intrRateType;

            @JsonProperty("intr_rate_type_nm")
            private String intrRateTypeNm;

            @JsonProperty("intr_rate")
            private String intrRate;

            @JsonProperty("intr_rate2")
            private String intrRate2;

            @JsonProperty("rsrv_type")
            private String rsrvType;

            @JsonProperty("rsrv_type_nm")
            private String rsrvTypeNm;

            @JsonProperty("mrtg_type")
            private String mrtgType;

            @JsonProperty("mrtg_type_nm")
            private String mrtgTypeNm;

            @JsonProperty("rpay_type")
            private String rpayType;

            @JsonProperty("rpay_type_nm")
            private String rpayTypeNm;

            @JsonProperty("lend_rate_type")
            private String lendRateType;

            @JsonProperty("lend_rate_type_nm")
            private String lendRateTypeNm;

            @JsonProperty("lend_rate_min")
            private String lendRateMin;

            @JsonProperty("lend_rate_max")
            private String lendRateMax;

            @JsonProperty("lend_rate_avg")
            private String lendRateAvg;

        }
    }
}
