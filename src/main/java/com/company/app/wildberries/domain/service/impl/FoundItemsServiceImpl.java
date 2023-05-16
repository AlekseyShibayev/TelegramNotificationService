package com.company.app.wildberries.domain.service.impl;

import com.company.app.wildberries.domain.entity.FoundItem;
import com.company.app.wildberries.domain.repository.FoundItemRepository;
import com.company.app.wildberries.domain.service.api.FoundItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class FoundItemsServiceImpl implements FoundItemsService {

	@Autowired
	private FoundItemRepository foundItemRepository;

	@Transactional
	@Override
	public void save(FoundItem foundItem) {
		foundItemRepository.save(foundItem);
	}

	@Transactional
	@Override
	public void saveAll(Collection<FoundItem> foundItems) {
		foundItemRepository.saveAll(foundItems);
	}

	@Transactional
	@Override
	public List<FoundItem> getAll() {
		return foundItemRepository.findAll();
	}
}
