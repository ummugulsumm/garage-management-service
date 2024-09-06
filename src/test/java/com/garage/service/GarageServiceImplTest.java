package com.garage.service;

import com.garage.exception.InvalidVehicleException;
import com.garage.exception.VehicleNotFoundException;
import com.garage.model.VehicleModel;
import com.garage.service.impl.GarageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GarageServiceImplTest {

    private GarageServiceImpl garageService;

    @BeforeEach
    void setUp() {
        garageService = new GarageServiceImpl();
    }

    @Test
    void testParkSuccess() {
        VehicleModel vehicle = new VehicleModel();
        vehicle.setPlateNumber("34-ABC-1234");
        vehicle.setType("Car");
        vehicle.setColor("Red");

        String result = garageService.park(vehicle);
        assertTrue(result.contains("Allocated 1 slot."));
    }

    @Test
    void testParkFailure() {
        VehicleModel vehicle = new VehicleModel();
        vehicle.setPlateNumber("34-INVALID-123");
        vehicle.setType("Car");
        vehicle.setColor("Red");

        Exception exception = assertThrows(InvalidVehicleException.class, () -> garageService.park(vehicle));
        assertTrue(exception.getMessage().contains("Invalid plate number format"));
    }

    @Test
    void testLeaveSuccess() {
        garageService.leave(1);
        assertTrue(true);
    }

    @Test
    void testLeaveFailure() {
        Exception exception = assertThrows(VehicleNotFoundException.class, () -> garageService.leave(-1));
        assertTrue(exception.getMessage().contains("Invalid vehicle number provided."));
    }

    @Test
    void testGetStatus() {
        String status = garageService.getStatus();
        assertNotNull(status);
        assertTrue(status.contains("Status:"));
    }
}
