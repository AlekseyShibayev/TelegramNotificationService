package com.company.app.wildberries.domain.service.api;

import com.company.app.wildberries.domain.entity.FoundItem;

import java.util.Collection;
import java.util.List;

public interface FoundItemsService {

	void save(FoundItem foundItem);

	void saveAll(Collection<FoundItem> foundItems);

	List<FoundItem> getAll();
}
