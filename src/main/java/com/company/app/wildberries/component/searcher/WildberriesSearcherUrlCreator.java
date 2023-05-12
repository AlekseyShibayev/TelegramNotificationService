package com.company.app.wildberries.component.searcher;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WildberriesSearcherUrlCreator {

	private static final String URL = "https://search.wb.ru/exactmatch/ru/male/v4/search?" +
			"appType=1&curr=rub&dest=-3827418&fbrand=21;61;671;60361" +
			"&regions=80,64,38,4,115,83,33,68,30,86,40,1,66,48,110,31,22,114" +
			"&resultset=catalog&sort=popular&spp=22&suppressSpellcheck=false&xsubject=105";

	public static String create(String gender) {
		String withGender = withGender(gender);
		String page = "&page=%s";
		String query = "&query=кроссовки";
		return URL + withGender + query + page;
	}

	private static String withGender(String gender) {
		int i = gender.equals("male") ? 1 : 2;
		return "&fkind=" + i;
	}
}
