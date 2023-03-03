package com.company.app.service.wildberries;

import com.company.app.AbstractTest;
import com.company.app.entity.Lot;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WildberriesFacadeTest extends AbstractTest {

	@Autowired
	private WildberriesFacade wildberriesFacade;

	@Test
	public void doMainLogicTest() {
		List<Lot> desiredLots = wildberriesFacade.getDesiredLots();
		Assert.assertEquals(desiredLots.size(), 1);
	}
}