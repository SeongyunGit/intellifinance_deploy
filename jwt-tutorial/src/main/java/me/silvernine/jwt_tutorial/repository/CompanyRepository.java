package me.silvernine.jwt_tutorial.repository;

import me.silvernine.jwt_tutorial.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
