package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.enrich_inpl;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HabrUserEnrich implements Enrich {

    @Autowired
    private HabrRepository habrRepository;
    @Autowired
    private HabrUserRepository habrUserRepository;

    @Setter
    private String name;

    @Override
    public void accept(Habr habr) {
        HabrUser user = new HabrUser()
            .setHabr(habr)
            .setName(name);

        habrUserRepository.save(user);
        habr.getHabrUsers().add(user);
        habrRepository.save(habr);
    }

}