package com.company.app.habr.infrastructure.test_entity_factory;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactory {

    private final HabrRepository habrRepository;
    private final UserRepository userRepository;

    public Habr createMinimumPosibleHabr(Status status) {
        return habrRepository.save(new Habr().setStatus(status));
    }

    public void addUser(Habr habr) {
        HabrUser user = new HabrUser().setHabr(habr);
        userRepository.save(user);
        habr.getHabrUsers().add(user);
        habrRepository.save(habr);
    }

}