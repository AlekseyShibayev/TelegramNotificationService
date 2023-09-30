package com.company.app.wildberries_desire_lot.domain.repository;

import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DesireRepository extends JpaRepository<Desire, Long>, JpaSpecificationExecutor<Desire> {

}
