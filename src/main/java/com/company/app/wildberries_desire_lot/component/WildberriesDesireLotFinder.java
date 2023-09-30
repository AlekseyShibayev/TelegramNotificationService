package com.company.app.wildberries_desire_lot.component;

import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotFinder {

    private final DesireRepository desireRepository;

    public List<Desire> find() {

        return null;
    }

}
