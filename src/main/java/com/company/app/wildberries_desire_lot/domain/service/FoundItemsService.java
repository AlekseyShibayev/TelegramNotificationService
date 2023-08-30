package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.repository.FoundItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoundItemsService {

    private final FoundItemRepository foundItemRepository;

    public void save(FoundItem foundItem) {
        foundItemRepository.save(foundItem);
    }

    public void saveAll(Collection<FoundItem> foundItems) {
        foundItemRepository.saveAll(foundItems);
    }

    public List<FoundItem> getAll() {
        return foundItemRepository.findAll();
    }
}
