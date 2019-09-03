package com.example.secondmicroservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	private Map<Integer, Integer> balance = new HashMap<>();
	
	@RequestMapping(path = "/accountbalance/{id}",method = RequestMethod.GET)
	public Integer getAccontBalance(@PathVariable Integer id) {
		return balance.get(id);
	}
	
	@RequestMapping(path = "/accountbalance", method = RequestMethod.POST,consumes={"application/json","application/xml"})
	public void addBalance(@RequestBody Profile profile) {
		balance.put(profile.getAccountNo(), profile.getBalance());
	}
	
	
}
