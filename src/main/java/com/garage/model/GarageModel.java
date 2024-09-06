package com.garage.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GarageModel {


    private int totalNumberSlot = 10;

    private final Map<Integer, VehicleModel> slots = new HashMap<>();

    private static GarageModel garage;

    private GarageModel() {
        for (int i = 1; i <= totalNumberSlot; i++) {
            slots.put(i, null);
        }
    }

    public static synchronized GarageModel getGarage() {
        if(garage == null) {
            garage = new GarageModel();
        }
        return garage;
    }
}
