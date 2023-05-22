package com.company.app.wildberries_searcher.component.data;

import com.company.app.wildberries_searcher.domain.entity.SearchData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WildberriesSearcherContainer {

	private String chatName;
	private String footSize;
	private String dressSize;
	private String gender;
	private String supplier;
	private String greedIndex;

	public static WildberriesSearcherContainer of(SearchData searchData) {
		WildberriesSearcherContainer container = new WildberriesSearcherContainer();
		BeanUtils.copyProperties(searchData, container);
		return container;
	}
}
