package com.mfano.mfano.mpesa;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/mpesa/callback")
	public ResponseEntity<String> handleMpesaCallback(@RequestBody String callbackData) {
		try {
			// Process and save to database
			mpesa.saveTransactionResponse(new JSONObject(callbackData));
			return ResponseEntity.ok("Received");
		} catch (Exception e) {
			// Log error and handle exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing callback");
		}
	}

}