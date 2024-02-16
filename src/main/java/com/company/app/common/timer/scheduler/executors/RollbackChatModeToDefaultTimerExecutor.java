package com.company.app.common.timer.scheduler.executors;

import java.time.ZonedDateTime;
import java.util.List;

import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.TimerType;
import com.company.app.common.timer.domain.repository.TimerRepository;
import com.company.app.common.timer.scheduler.TimerExecutor;
import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class RollbackChatModeToDefaultTimerExecutor implements TimerExecutor {

    @Value("${timer.rollback_chat_mode_to_default.timeout_minutes}")
    private Integer timeout;
    private static final TimerType TIMER_TYPE = TimerType.ROLLBACK_CHAT_MODE_TO_DEFAULT;

    private final ChatRepository chatRepository;
    private final TimerRepository timerRepository;
    private final ModeRepository modeRepository;

    @Override
    public TimerType getType() {
        return TIMER_TYPE;
    }

    /**
     * time: _______create______now_____create + minutes_____
     * time: _______create______create + minutes_______now_____
     */
    @Transactional
    @PerformanceLogAnnotation
    public void execute() {
        ZonedDateTime now = ZonedDateTime.now();

        List<Timer> timers = timerRepository.findAllByTimerType(TIMER_TYPE);

        timers.stream()
            .filter(timer -> timer.getCreateDate().plusMinutes(timeout).isBefore(now))
            .forEach(this::executeOne);
    }

    private void executeOne(Timer timer) {
        chatRepository.findByChatName(timer.getChatName())
            .ifPresent(chat -> {
                Mode mode = chat.getMode();
                if (!mode.getType().equals(ModeType.DEFAULT.name())) {
                    Mode defaultMode = modeRepository.findByType(ModeType.DEFAULT);
                    chat.setMode(defaultMode);
                    chatRepository.save(chat);
                }
            });
        timerRepository.delete(timer);
    }

}
