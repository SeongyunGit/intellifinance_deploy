package me.silvernine.jwt_tutorial.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_option")
@Getter
@Setter
@NoArgsConstructor
public class CompanyOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fin_co_no")
    @JsonBackReference
    private Company company;

    private String areaCd;
    private String areaNm;
    private String exisYn;
    private String dclsMonth;
}