package com.devops;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyService {

	@RequestMapping("/")	
	public String hello()
	{
		LocalHostUtil.main();
	}
	
}
