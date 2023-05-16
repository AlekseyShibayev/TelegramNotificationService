package com.company.app.wildberries.domain.repository;

import com.company.app.wildberries.domain.entity.FoundItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoundItemRepository extends CrudRepository<FoundItem, Long> {

	List<FoundItem> findAll();
}
