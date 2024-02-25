package com.company.app.common.tool;

import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaFighter {

    private static final Random RANDOM = new Random();

    /**
     * sleep random mc [of-to]
     */
    @SneakyThrows
    public void fight(int of, int to) {
        int sleepTime = of + getRandomInt(to - of);
        doLog(of, to, sleepTime);
        Thread.sleep(sleepTime);
    }

    private void doLog(int of, int to, int sleepTime) {
        if (log.isDebugEnabled()) {
            Thread thread = Thread.currentThread();
            log.debug("[{}]: Сплю от [{}] ms до [{}] ms: [{}] ms.",
                thread.getName(), of, to, sleepTime);
        }
    }

    private int getRandomInt(int required) {
        int n = RANDOM.nextInt(required);
        return n + 1;
    }

}