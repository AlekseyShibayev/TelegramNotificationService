package com.company.app.wildberries_desire_lot.domain.repository;

import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DesireRepository extends JpaRepository<Desire, Long>, JpaSpecificationExecutor<Desire> {

    @Query("from Desire d join d.desireLot dl where d.price >= dl.price")
    List<Desire> findWithDesirePriceGreaterThenRealPrice();

}
