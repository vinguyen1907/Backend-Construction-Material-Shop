package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Integer> {

}
