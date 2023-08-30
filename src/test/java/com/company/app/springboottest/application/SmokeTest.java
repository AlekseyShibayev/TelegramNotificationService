package com.company.app.springboottest.application;

import com.company.app.infrastructure.SpringBootTestApplicationContext;

class SmokeTest extends SpringBootTestApplicationContext {

//    @LocalServerPort
//    Integer port;
//    @Autowired
//    TestRestTemplate testRestTemplate;
//
//    @Autowired
//    TelegramFacade telegramFacade;
//    @Autowired
//    HistoryRepository historyRepository;
//    @Autowired
//    ChatService chatService;
//    @Autowired
//    InitialChatRegistry initialChatRegistry; // зовем его, потому что в тестах все ленивые, и он нам не создал стартовые чаты
//
//    @Test
//    void historyRepositorySmokeTest() {
//        historyRepository.deleteAll();
//
//        List<Chat> chatList = chatService.getAll();
//
//        historyRepository.save(History.builder()
//                .chat(chatList.get(0))
//                .message("0. Успешное сохранение в бд.")
//                .build());
//
//        Assertions.assertEquals(1, Lists.newArrayList(historyRepository.findAll()).size());
//    }
//
//    @Test
//    void notificationServiceSmokeTest() {
//        telegramFacade.writeToEveryone("1. Тестовое приложение поднялось.");
//
//        List<History> all = historyRepository.findAll();
//        Assertions.assertNotNull(all);
//    }
//
//    @Test
//    void controllerSmokeTest() {
//        String message = String.format("2. На порту %s успешно поднялось тестовое приложение.", port);
//        ResponseEntity<Object> entity = testRestTemplate.getForEntity(String.format("/telegram/say?message=%s", message), Object.class);
//        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
//    }
}