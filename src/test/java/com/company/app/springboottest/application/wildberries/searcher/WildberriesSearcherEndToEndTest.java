package com.company.app.springboottest.application.wildberries.searcher;

import com.company.app.springboottest.application.SpringBootTestApplicationContext;

class WildberriesSearcherEndToEndTest extends SpringBootTestApplicationContext {

//    @Autowired
//    private WildberriesSearcher wildberriesSearcher;
//    @MockBean
//    private WildberriesSearcherExtractor wildberriesSearcherExtractor;
//
//    @Test
//    void searcher_can_search_test() {
//        WildberriesSearcherContext wildberriesSearcherContainer = WildberriesSearcherContext.builder()
//                .chatName("653606407")
//                .gender("female")
//                .footSize("45")
//                .dressSize("46;48")
//                .supplier("5472")
//                .greedIndex("1.00")
//                .build();
//
//        ResponseProducts responseProductsMock = new ResponseProducts()
//                .setId(101304613)
//                .setSizes(List.of(
//                        Size.builder().name("46").build(),
//                        Size.builder().name("48").build(),
//                        Size.builder().name("50").build()
//                ))
//                .setRating("5")
//                .setFeedbacks("20")
//                .setSalePriceU(100);
//
//        PriceHistory priceHistoryMock = PriceHistory.builder()
//                .price(Price.builder().rub("1000").build())
//                .build();
//
//        Mockito.when(wildberriesSearcherExtractor.extractResponseProducts(Mockito.anyString())).thenReturn(List.of(responseProductsMock));
//        Mockito.when(wildberriesSearcherExtractor.extractPriceHistory(Mockito.anyString())).thenReturn(List.of(priceHistoryMock));
//
//        List<WildberriesLinkDto> dtoList = wildberriesSearcher.search(wildberriesSearcherContainer);
//
//        Assertions.assertTrue(dtoList.size() > 0);
//    }
}
