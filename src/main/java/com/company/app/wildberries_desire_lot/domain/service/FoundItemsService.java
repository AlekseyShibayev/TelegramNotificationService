package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.core.util.Collections;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.repository.FoundItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoundItemsService {

    private final FoundItemRepository foundItemRepository;

    public void saveAll(Collection<FoundItem> foundItems) {
        if (Collections.isEmpty(foundItems)) {
            log.debug("can't save, cause found items is empty");
        } else {
            log.debug("try to save [{}] found items", foundItems.size());
            foundItemRepository.saveAll(foundItems);
        }
    }

    public List<FoundItem> findAll() {
        List<FoundItem> all = foundItemRepository.findAll();
        log.debug("find [{}]", all.size());
        return all;
    }

}
