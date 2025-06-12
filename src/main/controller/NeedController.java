package com.meenashree.disasterresponse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.meenashree.disasterresponse.model.Need;
import com.meenashree.disasterresponse.service.DynamoDbService;
import com.meenashree.disasterresponse.service.S3Service;
import com.meenashree.disasterresponse.service.SnsService;


@RestController
@RequestMapping("/api/needs")
public class NeedController {

    private final DynamoDbService dynamoDbService;
    private final S3Service s3Service;
    private final SnsService snsService;

    @Autowired
    public NeedController(DynamoDbService dynamoDbService, S3Service s3Service, SnsService snsService) {
        this.dynamoDbService = dynamoDbService;
        this.s3Service = s3Service;
        this.snsService = snsService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
public ResponseEntity<String> createNeed(
        @RequestParam("location") String location,
        @RequestParam("type") String type,
        @RequestParam("description") String description,
        @RequestParam("phone") String phone,
        @RequestPart("file") MultipartFile file) {
    try {
        String fileUrl = s3Service.uploadFile(file);

        Need need = new Need();
        need.setLocation(location);
        need.setType(type);
        need.setDescription(description);
        need.setFileUrl(fileUrl);

        dynamoDbService.saveNeed(need);
        snsService.publishNotification("New need created: " + type + " at " + location);
        return new ResponseEntity<>("Need created successfully", HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>("Failed to create need: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("/submitReport")
public String handleReport(
    @RequestParam("location") String location,
    @RequestParam("type") String type,
    @RequestParam("description") String description,
    @RequestParam("photo") MultipartFile photo) {

    System.out.println("Location: " + location);
    System.out.println("Type: " + type);
    System.out.println("Description: " + description);
    System.out.println("Photo Name: " + photo.getOriginalFilename());

    return "Report Received Successfully!";
}
@GetMapping("/all")
public ResponseEntity<List<Need>> getAllNeeds() {
    List<Need> needs = dynamoDbService.getAllNeeds();
    return ResponseEntity.ok(needs);
}
}

    
