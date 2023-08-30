package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.wildberries_desire_lot.domain.dto.LotDto;
import com.company.app.wildberries_desire_lot.domain.entity.Lot;
import com.company.app.wildberries_desire_lot.domain.repository.LotRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    public Long create(LotDto lotDto) {
        Lot lot = Lot.builder().build();
        BeanUtils.copyProperties(lotDto, lot);
        return lotRepository.save(lot).getId();
    }

    public Lot read(Long id) {
        Optional<Lot> optional = lotRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ObjectNotFoundException(id, Lot.class.getName());
        }
    }

    public Boolean update(Long id, LotDto lotDto) {
        Lot lot = Lot.builder()
                .id(id)
                .build();
        BeanUtils.copyProperties(lotDto, lot);
        lotRepository.save(lot);
        return true;
    }

    public Boolean delete(Long id) {
        lotRepository.deleteById(id);
        return true;
    }

    public List<Lot> getAll() {
        return lotRepository.findAll();
    }

    public Long create(String name, String price, String discount) {
        Lot lot = Lot.builder()
                .article(name)
                .desiredPrice(price)
                .discount(discount)
                .build();
        return lotRepository.save(lot).getId();
    }

}
