package org.tes.productretrieverservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.service.AuthCodeService;

import java.util.Optional;

@RequestMapping("/secured/auth-code")
@RestController
public class AuthCodeController {
    private final AuthCodeService service;

    @Autowired
    public AuthCodeController(AuthCodeService service) {
        this.service = service;
    }

    @GetMapping() // POST is not used because of eBay's API
    public AuthCode save(
            @RequestParam String code,
            @RequestParam String expires_in
    ) {
        return service.save(Optional.of(new AuthCode(
                code,
                Integer.parseInt(expires_in)
        )));
    }

    @GetMapping("/latest")
    public AuthCode findLatest() {
        return service.findLatest();
    }
}
