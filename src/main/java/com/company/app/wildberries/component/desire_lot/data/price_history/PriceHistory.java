package com.company.app.wildberries.component.desire_lot.data.price_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory {

	String dt;
	Price price;
}
