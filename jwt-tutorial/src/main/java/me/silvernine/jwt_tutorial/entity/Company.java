package me.silvernine.jwt_tutorial.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    private String finCoNo;  // fin_co_no를 PK로 사용
    private String dclsMonth;
    private String korCoNm;
    private String dclsChrgMan;
    private String hompUrl;
    private String calTel;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CompanyOption> options = new ArrayList<>();
}
