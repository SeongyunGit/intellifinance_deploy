package me.silvernine.jwt_tutorial.repository;

import me.silvernine.jwt_tutorial.entity.BankOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankOptionRepository extends JpaRepository<BankOption, Long> {

}
