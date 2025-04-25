package com.krisnaajiep.unitconverter.util;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 26/04/25 01.00
@Last Modified 26/04/25 01.00
Version 1.0
*/

import java.util.LinkedHashMap;
import java.util.Map;

public class ConversionMap {
    private final static Map<String, Map<String, Object>> LENGTH_UNITS = new LinkedHashMap<>();

    private static void setLengthUnits() {
        LENGTH_UNITS.put("millimeter", Map.of("factor", 0.000001, "symbol", "mm"));
        LENGTH_UNITS.put("centimeter", Map.of("factor", 0.00001, "symbol", "cm"));
        LENGTH_UNITS.put("meter", Map.of("factor", 0.001, "symbol", "m"));
        LENGTH_UNITS.put("kilometer", Map.of("factor", 1.0, "symbol", "km"));
        LENGTH_UNITS.put("inch", Map.of("factor", 0.0000254, "symbol", "km"));
        LENGTH_UNITS.put("foot", Map.of("factor", 0.0003048, "symbol", "ft"));
        LENGTH_UNITS.put("yard", Map.of("factor", 0.0009144, "symbol", "yd"));
        LENGTH_UNITS.put("mile", Map.of("factor", 1.60934, "symbol", "mi"));
    }

    public static Map<String, Map<String, Object>> getLengthUnits() {
        setLengthUnits();
        return LENGTH_UNITS;
    }
}
