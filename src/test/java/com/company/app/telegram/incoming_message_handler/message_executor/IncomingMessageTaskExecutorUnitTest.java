package com.company.app.telegram.incoming_message_handler.message_executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IncomingMessageTaskExecutorUnitTest {

    @Test
    void convertToArticle() {
        String before = """
                        Шоколад "Темный (70%)" 1кг Для кулинарии Yummy mood
                        https://wildberries.ru/catalog/13341986/detail.aspx
                        """;

        String after = IncomingMessageTaskExecutor.convertToArticle(before);

        Assertions.assertEquals("13341986", after);
    }

}