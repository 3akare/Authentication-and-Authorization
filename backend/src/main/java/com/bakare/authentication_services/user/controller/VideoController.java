package com.bakare.authentication_services.user.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    @GetMapping("/rickRoll")
    public ResponseEntity<Resource> getVideo() throws IOException{
        Resource video = new ClassPathResource("static/videos/rick-roll.mp4");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
        return new ResponseEntity<>(video, headers, HttpStatus.OK);
    }
}
