package com.yassir.budgetbuddy.category.Repository;

import com.yassir.budgetbuddy.category.bean.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeSourceRepository extends JpaRepository<IncomeSource,Integer> {

    Optional<IncomeSource> findByName(String name);

}
