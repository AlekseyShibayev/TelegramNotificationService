package com.company.app.telegram.domain.repository;

import com.company.app.core.exception.DeveloperMistakeException;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeRepository extends JpaRepository<Mode, Long> {

    default Mode findByType(ModeType modeType) {
        return this.findByType(modeType.getType())
                .orElseThrow(() -> new DeveloperMistakeException("[%s] mode must be".formatted(modeType.getType())));
    }

    Optional<Mode> findByType(String type);

}
