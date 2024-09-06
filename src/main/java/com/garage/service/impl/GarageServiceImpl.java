package com.garage.service.impl;

import com.garage.exception.InvalidVehicleException;
import com.garage.exception.VehicleNotFoundException;
import com.garage.model.GarageModel;
import com.garage.model.VehicleModel;
import com.garage.service.GarageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GarageServiceImpl implements GarageService {

    private final GarageModel garage = GarageModel.getGarage();


    private final Map<String, Integer> vehicleSizeMap = Map.of(
            "car", 1,
            "jeep", 2,
            "truck", 4
    );

    @Override
    public String park(VehicleModel vehicle) {

        log.info("Park method is started for vehicle: {}", vehicle);

        validateVehicle(vehicle);

        int vehicleSize = vehicleSizeMap.get(vehicle.getType().toLowerCase());
        int startSlot = getAvailableSlot(vehicleSize);


        if (startSlot != -1) {
            for (int i = startSlot; i < startSlot + vehicleSize; i++) {
                garage.getSlots().put(i, vehicle);
                log.debug("Vehicle {} parked at slot {}", vehicle, i);
            }
            log.info("Vehicle {} successfully parked", vehicle);
            return getMessage(vehicleSize);
        }
        log.warn("No available slot for vehicle: {}", vehicle);
        return getMessage(-1);
    }

    @Override
    public void leave(int vehicleNumber) {

        log.info("Leave method is started for vehicle number: {}", vehicleNumber);

        if (vehicleNumber <= 0 || vehicleNumber > getVehicleAndSlots().size()) {
            log.error("Invalid vehicle number: {}", vehicleNumber);
            throw new VehicleNotFoundException("Invalid vehicle number provided.");
        }

        List<Integer> slots = getVehicleAndSlots().get(vehicleNumber - 1).getValue();
        log.debug("Slots to be freed: {}", slots);

        for(int i = slots.get(0); i <= slots.get(slots.size() - 1); i++) {
               garage.getSlots().put(i, null);
               log.debug("Freed slot {}", i);
       }
        log.info("Vehicle at number {} successfully left the garage", vehicleNumber);
    }

    @Override
    public String getStatus() {
        log.info("Get status method is started");
        String status = "Status:\n" + getVehicleAndSlots().stream()
                .map(entry -> entry.getKey().getPlateNumber() + " " + entry.getKey().getColor() + " " + entry.getValue())
                .collect(Collectors.joining("\n"));
        log.debug("Current garage status: {}", status);
        return status;
    }

    private List<Map.Entry<VehicleModel, List<Integer>>> getVehicleAndSlots() {
        log.debug("Getting vehicle and slots information");

        Map<VehicleModel, List<Integer>> vehicleAndSlots = new LinkedHashMap<>();
        for (Map.Entry<Integer, VehicleModel> entry : garage.getSlots().entrySet()) {
            VehicleModel vehicle = entry.getValue();
            if (vehicle != null) {
                vehicleAndSlots.putIfAbsent(vehicle, new ArrayList<>());
                vehicleAndSlots.get(vehicle).add(entry.getKey());
            }
        }
        log.debug("Retrieved vehicle and slots: {}", vehicleAndSlots);
        return new ArrayList<>(vehicleAndSlots.entrySet());
    }


    private int getAvailableSlot(int vehicleSize) {
        log.info("Finding available slots for vehicle size: {}", vehicleSize);

        int requiredSlots = 0;
        int startSlot = -1;

        for (int i = 0; i <= garage.getTotalNumberSlot(); i++) {
            if (garage.getSlots().get(i) == null) {
                if(requiredSlots == 0) {
                    startSlot = i;
                }
                requiredSlots++;
                if (requiredSlots == vehicleSize + 1) {
                    log.info("Found available slot starting at {}", startSlot + 1);
                    return startSlot + 1;
                }
            } else {
                requiredSlots = 0;
            }
        }
        log.warn("No available slots found for vehicle size: {}", vehicleSize);
        return -1;
    }

    private String getMessage(int vehicleSize) {
        log.debug("Generating message for vehicle size: {}", vehicleSize);
        if(vehicleSize == -1) {
            return "Garage is full.\n";
        }
        else if(vehicleSize == 1) {
            return "Allocated " + vehicleSize + " slot.\n";
        } else {
            return "Allocated " + vehicleSize + " slots.\n";
        }

    }



    private void validateVehicle(VehicleModel vehicle) {
        if (vehicle == null) {
            log.error("Vehicle object is null");
            throw new InvalidVehicleException("Vehicle information is required.");
        }

        String type = vehicle.getType();
        String plateNumber = vehicle.getPlateNumber();

        if (!StringUtils.hasText(type) || !StringUtils.hasText(plateNumber)) {
            log.error("Invalid vehicle type or plate number: {}", vehicle);
            throw new InvalidVehicleException("Vehicle type and plate number must be provided.");
        }


        if (!vehicleSizeMap.containsKey(type.toLowerCase())) {
            log.error("Invalid vehicle type: {}", type);
            throw new InvalidVehicleException("Invalid vehicle type: " + type + ". Allowed types are: " + vehicleSizeMap.keySet());
        }

        String plateRegex = "^\\d{2}-[A-Z]{2,3}-\\d{4}$";
        if (!plateNumber.matches(plateRegex)) {
            log.error("Invalid plate number format: {}", plateNumber);
            throw new InvalidVehicleException("Invalid plate number format: " + plateNumber + ". Expected format is: 34-AB-1234 or 34-ABC-1234");
        }

        log.info("Vehicle validation passed for: {}", vehicle);
    }

}
