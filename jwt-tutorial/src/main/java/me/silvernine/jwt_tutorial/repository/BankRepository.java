package me.silvernine.jwt_tutorial.repository;

import me.silvernine.jwt_tutorial.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}
