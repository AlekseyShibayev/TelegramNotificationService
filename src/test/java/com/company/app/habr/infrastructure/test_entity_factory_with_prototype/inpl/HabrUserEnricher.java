package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.inpl;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class HabrUserEnricher {

    private final HabrRepository habrRepository;
    private final HabrUserRepository habrUserRepository;

    public void enrichHabrByHabrUserName(Habr habr, String name) {
        HabrUser user = new HabrUser()
            .setHabr(habr)
            .setName(name);

        habrUserRepository.save(user);
        habr.getHabrUsers().add(user);
        habrRepository.save(habr);
    }

}