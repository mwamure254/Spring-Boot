package com.mfano.mfano.mpesa;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;

@Service
public class PesaService {
    public static String toBase64String(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.ISO_8859_1));
    }

    public static String getStkPushPassword(String shortCode, String passKey, String timestamp) {
        String concatenatedString = String.format("%s%s%s", shortCode, passKey, timestamp);
        return toBase64String(concatenatedString);
    }

    public static String getTransactionTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    @Value("${mpesa.consumer-key}")
    private String consumerKey;
    @Value("${mpesa.consumer-secret}")
    private String consumerSecret;

    @Value("${mpesa.lipa-shortcode}")
    private String lipaShortcode;
    @Value("${mpesa.lipa-shortcode-initiator}")
    private String lipaShortcodeInitiator;
    @Value("${mpesa.shortcode}")
    private String shortcode;
    @Value("${mpesa.consumer-passkey}")
    private String passKey;

    private String getAccessToken() {
        String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
        String auth = consumerKey + ":" + consumerSecret;
        String encodedAuth = toBase64String(auth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);

        JSONObject jsonResponse = new JSONObject();

        HttpEntity<String> request = new HttpEntity<>(jsonResponse.toString(),headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return jsonResponse.getAsString("access-token");
        }
        return null;
    }

    public String initiateSTKPush(String phoneNumber, String amount, String accountNumber) {
        String token = getAccessToken();
        if (token == null) {
            return "Failed to get access token";
        }
        String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + token);
        headers.set("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("BusinessShortCode", lipaShortcode);
        body.put("Password", getStkPushPassword(shortcode, passKey, getTransactionTimestamp())); // Encode correctly
        body.put("Timestamp", getTransactionTimestamp()); // Generate correctly
        body.put("TransactionType", "CustomerPayBillOnline");
        body.put("Amount", amount);
        body.put("PartyA", phoneNumber);
        body.put("PartyB", lipaShortcode);
        body.put("PhoneNumber", phoneNumber);
        body.put("CallBackURL", "https://yourdomain.com/callback");
        body.put("AccountReference", accountNumber);
        body.put("TransactionDesc", "Payment for testing");

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}