package com.company.app.wildberries.controller;

import com.company.app.wildberries.component.WildberriesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: comment
 *
 * @author user 29.04.2023   20:45
 */
@RestController
@RequestMapping("/wildberries")
public class WildberriesController {

	@Autowired
	WildberriesFacade wildberriesFacade;


}
