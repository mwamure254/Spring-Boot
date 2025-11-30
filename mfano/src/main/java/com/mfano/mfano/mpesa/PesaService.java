package com.mfano.mfano.mpesa;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mfano.mfano.security.ConstantS;

@Service
public class PesaService {    
    HttpEntity<String> request;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response;

    public static String toBase64String(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.ISO_8859_1));
    }

    public static String getStkPushPassword(String shortCode, String passKey, String timestamp) {
        String concatenatedString = "%s%s%s".formatted(shortCode, passKey, timestamp);
        return toBase64String(concatenatedString);
    }

    public static String getTransactionTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public String getAccessToken() {
        String auth = ConstantS.consumerKey + ":" + ConstantS.consumerSecret;
        String encodedAuth = toBase64String(auth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.set("Content-Type", "application/json");

		request = new HttpEntity<>(new JSONObject().toString(), headers);
		response = restTemplate.exchange(ConstantS.TOKEN_URL, HttpMethod.GET, request, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject jsonResponse = new JSONObject(response.getBody());
			
			return jsonResponse.getString("access_token");
		} else {
			// Handle error
			return "Error: " + response.getStatusCode() + " - " + response.getBody();
		}

    }

    public String initiateSTKPush(String phoneNumber, String amount) {
        String token = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("BusinessShortCode", ConstantS.shortcode);
        body.put("Password", getStkPushPassword(ConstantS.shortcode, ConstantS.passKey, getTransactionTimestamp())); // Encode correctly
        body.put("Timestamp", getTransactionTimestamp()); // Generate correctly
        body.put("TransactionType", "CustomerPayBillOnline");//"CustomerBuyGoodsOnline"//CustomerPayBillOnline
        body.put("Amount", amount);
        body.put("PartyA", phoneNumber);
        body.put("PartyB", ConstantS.shortcode);
        body.put("PhoneNumber", phoneNumber);
        body.put("CallBackURL", "http://localhost:2000/mpesa/callback");
        body.put("AccountReference", "test");
        body.put("TransactionDesc", "Payment for testing");

        request = new HttpEntity<>(body.toString(), headers);
        response = restTemplate.exchange(ConstantS.LIPA_NA_MPESA_URL, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
        	//return "Error: " + response.getStatusCode() + " - " + response.getBody();
        }

        //return response.getBody();
        return "redirect:/index";
    }
    
    @Autowired
    private CallRepository callRepository;
    
    public void saveTransactionResponse(JSONObject resultJson) {
    	CallData transaction = new CallData();
    	
    	transaction.setMerchantRequestID(resultJson.getString("MerchantRequestID"));
    	transaction.setCheckoutRequestID(resultJson.getString("CheckoutRequestID"));
    	transaction.setMpesaReceiptNumber(resultJson.getString("MpesaReceiptNumber"));
    	transaction.setResultCode(resultJson.getInt("ResultCode"));
    	transaction.setResultDesc(resultJson.getString("ResultDesc"));
    	transaction.setAmount(resultJson.getInt("Amount"));
    	transaction.setPhoneNumber(resultJson.getString("PhoneNumber"));
    	transaction.setTransactionDate(resultJson.getString("TransactionDate"));
    	
    	// Save to database
    	callRepository.save(transaction);
    }

}