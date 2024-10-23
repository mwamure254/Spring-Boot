package com.mfano.mfano.mpesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PesaController {

	@Autowired
	private PesaService mpesa;
	
	@PostMapping("/mPush")
	public void mPush(@RequestParam("phone") String phone, @RequestParam("amount") String amount) {
		try {
			mpesa.initiateSTKPush(phone, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}