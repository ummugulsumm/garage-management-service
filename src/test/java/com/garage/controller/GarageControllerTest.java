package com.garage.controller;

import com.garage.model.VehicleModel;
import com.garage.service.GarageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GarageControllerTest {

    @InjectMocks
    private GarageController garageController;

    @Mock
    private GarageService garageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPark() {
        VehicleModel vehicle = new VehicleModel();
        vehicle.setPlateNumber("34-ABC-1234");
        vehicle.setType("Car");
        vehicle.setColor("Red");

        when(garageService.park(any(VehicleModel.class))).thenReturn("Allocated 1 slot.\n");

        ResponseEntity<String> response = garageController.park(vehicle);
        assertEquals("Allocated 1 slot.\n", response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testLeave() {
        ResponseEntity<Void> response = garageController.leave(1);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetStatus() {
        when(garageService.getStatus()).thenReturn("Status:\n34-ABC-1234 Red [1]");

        ResponseEntity<String> response = garageController.getStatus();
        assertEquals("Status:\n34-ABC-1234 Red [1]", response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }
}
