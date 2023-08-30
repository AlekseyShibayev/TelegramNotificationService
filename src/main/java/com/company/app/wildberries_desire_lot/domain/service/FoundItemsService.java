package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;

import java.util.Collection;
import java.util.List;

public interface FoundItemsService {

    void save(FoundItem foundItem);

    void saveAll(Collection<FoundItem> foundItems);

    List<FoundItem> getAll();
}
