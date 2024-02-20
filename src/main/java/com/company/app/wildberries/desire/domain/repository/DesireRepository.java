package com.company.app.wildberries.desire.domain.repository;

import java.util.List;

import com.company.app.wildberries.desire.domain.entity.Desire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface DesireRepository extends JpaRepository<Desire, Long>, JpaSpecificationExecutor<Desire> {

    @Query("from Desire d join d.desireLot dl where d.price >= dl.price")
    List<Desire> findAllWithDesirePriceGreaterThenRealPrice();

    List<Desire> findAllByChatName(String chatName);

}
