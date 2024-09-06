package com.garage.controller;

import com.garage.model.VehicleModel;
import com.garage.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.*;

@RestController()
@RequestMapping("/garage")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @PostMapping("/park")
    public ResponseEntity<String> park(@RequestBody VehicleModel vehicle) {
        return new ResponseEntity<>(garageService.park(vehicle), OK);
    }

    @PatchMapping("/leave/{slotNumber}")
    public ResponseEntity<Void> leave(@PathVariable int slotNumber) {
        garageService.leave(slotNumber);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>(garageService.getStatus(), OK);
    }


}
