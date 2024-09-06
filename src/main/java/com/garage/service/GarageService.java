package com.garage.service;

import com.garage.model.VehicleModel;


public interface GarageService {
    String park(VehicleModel vehicle);

    void leave(int slotNumber);

    String getStatus();

}
