package com.company.app.telegram.integration.in.button.task_executor.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddDesireTaskExecutorUnitTest {

    @Test
    void convertToArticle() {
        String before = """
                        Шоколад "Темный (70%)" 1кг Для кулинарии Yummy mood
                        https://wildberries.ru/catalog/13341986/detail.aspx
                        """;

        String after = AddDesireTaskExecutor.convertToArticle(before);

        Assertions.assertEquals("13341986", after);
    }

}