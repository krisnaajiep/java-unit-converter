package com.krisnaajiep.unitconverter.service;

import java.util.Map;

record UnitInfo(double factor, String symbol) {
    static UnitInfo from(Map<String, Object> data) {
        return new UnitInfo(
                data.containsKey("factor") ? (double) data.get("factor") : 0.0,
                data.get("symbol").toString()
        );
    }
}
