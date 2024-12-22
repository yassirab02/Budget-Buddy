package com.yassir.budgetbuddy.category.Repository;

import com.yassir.budgetbuddy.category.bean.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyTypeRepository extends JpaRepository<CurrencyType,Integer> {

    Optional<CurrencyType> findByName(String name);

}
