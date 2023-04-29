package com.company.app.wildberries.repository;

import com.company.app.wildberries.entity.FoundItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoundItemRepository extends CrudRepository<FoundItem, Long> {

	List<FoundItem> findAll();
}
