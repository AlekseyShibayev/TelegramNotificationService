package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.wildberries_desire_lot.domain.dto.LotDto;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DesireLotService {

    @Autowired
    private DesireLotRepository desireLotRepository;

    public Long create(LotDto lotDto) {
        DesireLot lot = new DesireLot();
        BeanUtils.copyProperties(lotDto, lot);
        return desireLotRepository.save(lot).getId();
    }

    public DesireLot read(Long id) {
        Optional<DesireLot> optional = desireLotRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ObjectNotFoundException(id, DesireLot.class.getName());
        }
    }

    public Boolean update(Long id, LotDto lotDto) {
        DesireLot lot = new DesireLot()
                .setId(id);
        BeanUtils.copyProperties(lotDto, lot);
        desireLotRepository.save(lot);
        return true;
    }

    public Boolean delete(Long id) {
        desireLotRepository.deleteById(id);
        return true;
    }

    public List<DesireLot> getAll() {
        return desireLotRepository.findAll();
    }

    public Long create(String name, String price, String discount) {
        DesireLot lot = new DesireLot()
                .setArticle(name)
                .setDesiredPrice(price)
                .setDiscount(discount);
        return desireLotRepository.save(lot).getId();
    }

}
