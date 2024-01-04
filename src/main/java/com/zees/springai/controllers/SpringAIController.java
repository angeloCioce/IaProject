package com.zees.springai.controllers;

import com.zees.springai.services.SpringAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;



@RestController
@RequestMapping("/api/v1")
public class SpringAIController {

    @Autowired
    SpringAIService aiService;

    @GetMapping("/request")
    public String getQuestion(@RequestParam String topic) {
        return aiService.getRequest(topic);
    }

    @GetMapping("/research_media")
    public String getMedia(@RequestParam(name = "type") String type, @RequestParam(name = "category") String category, @RequestParam(name = "year") String year) {
        return aiService.getBestMedia(type, category, year);
    }

    @GetMapping(value = "/image", produces = "image/jpeg")
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(name = "topic") String topic) throws URISyntaxException {
        return ResponseEntity.ok().body(aiService.getImage(topic));
    }
}
