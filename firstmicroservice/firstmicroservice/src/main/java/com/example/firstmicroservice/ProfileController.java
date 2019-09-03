package com.example.firstmicroservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.firstmicroservice.model.Profile;

@RestController
public class ProfileController {
	RestTemplate restTemplate = new RestTemplate();
	ArrayList<Profile> list = new ArrayList<Profile>();
	int accountNumber = 1;

	@RequestMapping(path = "/profiles/{customerid}", method = RequestMethod.GET)
	public Profile getCustomerProfile(@PathVariable Integer customerid) {
		Optional<Profile> findFirst = list.stream().filter(p -> p.getAccountNo() == customerid).findFirst();
		if (findFirst.isPresent()) {
			Integer bal = restTemplate.getForObject(
					"http://localhost:8082/accountbalance/" + findFirst.get().getAccountNo(), Integer.class);
			findFirst.get().setBalance(bal == null ? 0 : bal);
			return findFirst.get();
		}
		return new Profile();
	}

	@RequestMapping(path = "/profiles", method = RequestMethod.POST, consumes = { "application/json",
			"application/xml" })
	public void addCustomer(@RequestBody Profile profile) throws Exception {
		profile.setAccountNo(accountNumber);
		accountNumber++;
		list.add(profile);
		restTemplate.postForObject("http://localhost:8082/accountbalance/" + profile.getAccountNo(), profile,
				Profile.class, Integer.class);
	}

	@RequestMapping(path = "/profiles/customerid", method = RequestMethod.GET)
	public List<Profile> getAllCustomer() {
		return list;
	}
}
