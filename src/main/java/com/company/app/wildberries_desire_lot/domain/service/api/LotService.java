package com.company.app.wildberries_desire_lot.domain.service.api;

import com.company.app.wildberries_desire_lot.domain.dto.LotDto;
import com.company.app.wildberries_desire_lot.domain.entity.Lot;

import java.util.List;

public interface LotService {

	Long create(LotDto lotDto);

	Lot read(Long id);

	Boolean update(Long id, LotDto lotDto);

	Boolean delete(Long id);

	List<Lot> getAll();

	Long create(String name, String price, String discount);
}
