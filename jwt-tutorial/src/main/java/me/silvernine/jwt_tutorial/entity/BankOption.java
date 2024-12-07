package me.silvernine.jwt_tutorial.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_option")
@Getter
@Setter
@NoArgsConstructor
public class BankOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fin_co_no")
    @JsonBackReference
    private Bank bank;

    private String dclsMonth;
    private String intrRateType;
    private String intrRateTypeNm;
    private String intrRate;
    private String intrRate2;
    private String rsrvType;
    private String rsrvTypeNm;
    private String mrtgType;
    private String mrtgTypeNm;
    private String rpayType;
    private String rpayTypeNm;
    private String lendRateType;
    private String lendRateTypeNm;
    private String lendRateMin;
    private String lendRateMax;
    private String lendRateAvg;

}
