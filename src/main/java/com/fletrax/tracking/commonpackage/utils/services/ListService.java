package com.fletrax.tracking.commonpackage.utils.services;

import org.springframework.stereotype.Service;

import com.fletrax.tracking.commonpackage.configuration.ListsConfig;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ListService {

    private ListsConfig listsConfig;

    public List<Map<String, Object>> getListByKey(String key) {
        Map<String, Map<String, Object>> map;

        // Get the map for the given key
        switch (key) {
            // vehicles service
            case "vehicle_fuel_types":
                map = listsConfig.getVehicleFuelTypes();
                break;
            case "vehicle_transmission_types":
                map = listsConfig.getVehicleTransmissionTypes();
                break;
            case "vehicle_car_types":
                map = listsConfig.getVehicleCarTypes();
                break;
            case "vehicle_statuses":
                map = listsConfig.getVehicleStatuses();
                break;
            case "vehicle_colors":
                map = listsConfig.getVehicleColors();
                break;
            case "vehicle_seats":
                map = listsConfig.getVehicleSeats();
                break;
            case "vehicle_types":
                map = listsConfig.getVehicleTypes();
                break;
            case "vehicle_car_brands":
                map = listsConfig.getVehicleCarBrands();
                break;
            // violations service
            case "violation_statuses":
                map = listsConfig.getViolationStatuses();
                break;
            // maintenances service
            case "maintenance_statuses":
                map = listsConfig.getMaintenanceStatuses();
                break;
            default:
                throw new IllegalArgumentException("Invalid key: " + key);
        }

        // Convert the map to a list of objects
        return map.entrySet().stream()
                .map(entry -> {
                    // Base result map
                    Map<String, Object> result = Map.of(
                            "name", entry.getValue().get("name"),
                            "value", entry.getValue().get("value"));

                    // Process "children" if it exists
                    Object childrenObject = entry.getValue().get("children");
                    if (childrenObject instanceof Map) {
                        // Cast children to a Map
                        Map<?, ?> childrenMap = (Map<?, ?>) childrenObject;

                        // Convert children map to a list
                        List<Map<String, Object>> children = childrenMap.entrySet().stream()
                                .map(childEntry -> {
                                    @SuppressWarnings("unchecked")
                                    Map<String, Object> child = (Map<String, Object>) childEntry.getValue();
                                    return Map.of(
                                            "name", child.get("name"),
                                            "value", child.get("value"));
                                })
                                .collect(Collectors.toList());

                        // Include "children" in the result map
                        return Map.of(
                                "name", entry.getValue().get("name"),
                                "value", entry.getValue().get("value"),
                                "children", children);
                    }

                    // Return result without "children" if none exists
                    return result;
                })
                .collect(Collectors.toList());

    }

}
