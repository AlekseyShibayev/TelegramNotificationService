package com.company.app.wildberries.domain.service.impl;

import com.company.app.wildberries.domain.dto.LotDto;
import com.company.app.wildberries.domain.entity.Lot;
import com.company.app.wildberries.domain.repository.LotRepository;
import com.company.app.wildberries.domain.service.api.LotService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotServiceImpl implements LotService {

	@Autowired
	private LotRepository lotRepository;

	@Override
	public Long create(LotDto lotDto) {
		Lot lot = Lot.builder().build();
		BeanUtils.copyProperties(lotDto, lot);
		return lotRepository.save(lot).getId();
	}

	@Override
	public Lot read(Long id) {
		Optional<Lot> optional = lotRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new ObjectNotFoundException(id, Lot.class.getName());
		}
	}

	@Override
	public Boolean update(Long id, LotDto lotDto) {
		Lot lot = Lot.builder()
				.id(id)
				.build();
		BeanUtils.copyProperties(lotDto, lot);
		lotRepository.save(lot);
		return true;
	}

	@Override
	public Boolean delete(Long id) {
		lotRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Lot> getAll() {
		return lotRepository.findAll();
	}

	@Override
	public Long create(String name, String price, String discount) {
		Lot lot = Lot.builder()
				.article(name)
				.desiredPrice(price)
				.discount(discount)
				.build();
		return lotRepository.save(lot).getId();
	}
}
