package com.mfano.mfano.mpesa;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PesaController {
    @Autowired
    PesaService pesaService;

    @PostMapping("/mTKPush")
    public String mPush(@RequestParam("phone") String phone, @RequestParam("amount") String amount,
            @RequestParam("account") String account) {
        pesaService.initiateSTKPush(phone, amount, account);

        return null;
    }

}