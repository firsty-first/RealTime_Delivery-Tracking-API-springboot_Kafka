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
        if(latitude>0 && longitude>0) {
            String location = "(" + latitude + "," + longitude + ")";

            // Send location data to Kafka topic
            this.kafkaService.updateLocation(location);

            return new ResponseEntity<>(Map.of("message", "Location updated"), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(Map.of("message", "Could fetch correct gps from end device"), HttpStatus.NOT_FOUND);
    }

}
