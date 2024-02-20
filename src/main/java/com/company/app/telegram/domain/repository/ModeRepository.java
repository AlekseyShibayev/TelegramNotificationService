package com.company.app.telegram.domain.repository;

import java.util.Optional;

import com.company.app.core.exception.DeveloperMistakeException;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModeRepository extends JpaRepository<Mode, Long> {

    default Mode findByType(ModeType modeType) {
        return this.findByType(modeType.name())
            .orElseThrow(() -> new DeveloperMistakeException("[%s] mode must be".formatted(modeType.name())));
    }

    Optional<Mode> findByType(String type);

}
