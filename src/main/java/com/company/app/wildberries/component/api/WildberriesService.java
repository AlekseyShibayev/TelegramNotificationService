package com.company.app.wildberries.component.api;

import com.company.app.wildberries.entity.FoundItem;
import com.company.app.wildberries.entity.Lot;

import java.util.List;

public interface WildberriesService {

	List<FoundItem> getDesiredLots();
}
