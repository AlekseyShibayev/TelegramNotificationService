package com.company.app.wildberries.service.api;

import com.company.app.wildberries.entity.FoundItem;

import java.util.Collection;
import java.util.List;

public interface FoundItemsService {

	void save(FoundItem foundItem);

	void saveAll(Collection<FoundItem> foundItems);

	List<FoundItem> getAll();
}
