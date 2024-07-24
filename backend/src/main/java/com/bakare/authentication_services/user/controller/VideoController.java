package com.bakare.authentication_services.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {

    @GetMapping("")
    public String video(@AuthenticationPrincipal UserDetails userDetails) {
        return "Video accessed by: " + userDetails.getUsername();
    }
}