package com.company.app.wildberries_desire_lot.domain.repository;

import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoundItemRepository extends CrudRepository<FoundItem, Long> {

    List<FoundItem> findAll();
}
