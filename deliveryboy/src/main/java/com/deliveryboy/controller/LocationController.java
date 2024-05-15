package com.deliveryboy.controller;

import com.deliveryboy.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private KafkaService kafkaService;
    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(@RequestParam("latitude") double latitude,
                                            @RequestParam("longitude") double longitude) {
        // Validate input parameters
        if (!isValidLatitude(latitude) || !isValidLongitude(longitude)) {
            return new ResponseEntity<>(Map.of("error", "Invalid location data"), HttpStatus.BAD_REQUEST);
        }

        // Construct location string
        String location = "(" + latitude + "," + longitude + ")";

        // Send location data to Kafka topic
        this.kafkaService.updateLocation(location);

        return new ResponseEntity<>(Map.of("message", "Location updated"), HttpStatus.OK);
    }

    private boolean isValidLatitude(double latitude) {
        // Validate latitude range (e.g., between -90 and 90 degrees)
        return latitude >= -90 && latitude <= 90;
    }

    private boolean isValidLongitude(double longitude) {
        // Validate longitude range (e.g., between -180 and 180 degrees)
        return longitude >= -180 && longitude <= 180;
    }


}
