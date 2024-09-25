package org.example.contest.domain.naturaldisaster.service;

import java.util.Map;

public interface NaturaldisasterService {

    Map<String, Object> getDisasterZones(double lat, double lng);
}
