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

/**
 * The {@code ConversionMap} class provides a utility for retrieving information about supported measurement units,
 * including length, weight, and temperature. Each unit is represented by its conversion factor (if applicable)
 * and a symbol, encapsulated in a nested map structure.
 */
public class ConversionMap {
    /**
     * A constant map that stores the defined length units along with their
     * corresponding conversion factors and symbols.
     */
    private final static Map<String, Map<String, Object>> LENGTH_UNITS = new LinkedHashMap<>();

    /**
     * A constant map that defines various weight units and their corresponding
     * conversion factors and symbols.
     */
    private final static Map<String, Map<String, Object>> WEIGHT_UNITS = new LinkedHashMap<>();

    /**
     * A constant map that stores temperature unit definitions.
     */
    private final static Map<String, Map<String, Object>> TEMPERATURE_UNITS = new LinkedHashMap<>();

    /**
     * Populates the {@code LENGTH_UNITS} map with predefined length unit definitions.
     * Each entry in the map represents a length unit, its conversion factor (relative to kilometers),
     * and its corresponding symbol.
     * <p>
     * This method is responsible for initializing the {@code LENGTH_UNITS} map with key-value pairs
     * where each key is the name of a length unit (e.g., "meter", "kilometer"), and the value
     * is a nested map containing "factor" and "symbol".
     */
    private static void setLengthUnits() {
        LENGTH_UNITS.put("millimeter", Map.of("factor", 0.000001, "symbol", "mm"));
        LENGTH_UNITS.put("centimeter", Map.of("factor", 0.00001, "symbol", "cm"));
        LENGTH_UNITS.put("meter", Map.of("factor", 0.001, "symbol", "m"));
        LENGTH_UNITS.put("kilometer", Map.of("factor", 1.0, "symbol", "km"));
        LENGTH_UNITS.put("inch", Map.of("factor", 0.0000254, "symbol", "in"));
        LENGTH_UNITS.put("foot", Map.of("factor", 0.0003048, "symbol", "ft"));
        LENGTH_UNITS.put("yard", Map.of("factor", 0.0009144, "symbol", "yd"));
        LENGTH_UNITS.put("mile", Map.of("factor", 1.60934, "symbol", "mi"));
    }

    /**
     * Retrieves a map containing predefined length units with their respective conversion factors and symbols.
     * Each key in the map represents the name of a length unit (e.g., "meter", "kilometer"), and the value
     * is a nested map that includes "factor" and "symbol"
     *
     * @return a map of length unit names mapped to details about their conversion factors and symbols
     */
    public static Map<String, Map<String, Object>> getLengthUnits() {
        setLengthUnits();
        return LENGTH_UNITS;
    }

    /**
     * Populates the {@code WEIGHT_UNITS} map with predefined weight unit definitions.
     * Each entry in the map represents a weight unit, its conversion factor (relative to kilograms),
     * and its corresponding symbol.
     * <p>
     * This method is responsible for initializing the {@code WEIGHT_UNITS} map with key-value pairs
     * where each key is the name of a weight unit (e.g., "gram", "pound"), and the value
     * is a nested map containing "factor" and "symbol".
     */
    private static void setWeightUnits() {
        WEIGHT_UNITS.put("milligram", Map.of("factor", 0.000001, "symbol", "mg"));
        WEIGHT_UNITS.put("gram", Map.of("factor", 0.001, "symbol", "g"));
        WEIGHT_UNITS.put("kilogram", Map.of("factor", 1.0, "symbol", "kg"));
        WEIGHT_UNITS.put("ounce", Map.of("factor", 0.0283495, "symbol", "oz"));
        WEIGHT_UNITS.put("pound", Map.of("factor", 0.453592, "symbol", "lb"));
    }

    /**
     * Retrieves a map containing predefined weight units with their respective conversion factors and symbols.
     * Each key in the map represents the name of a weight unit (e.g., "gram", "pound"), and the value
     * is a nested map that includes "factor" (conversion factor relative to kilograms) and "symbol".
     *
     * @return a map of weight unit names mapped to details about their conversion factors and symbols
     */
    public static Map<String, Map<String, Object>> getWeightUnits() {
        setWeightUnits();
        return WEIGHT_UNITS;
    }

    /**
     * Populates the {@code TEMPERATURE_UNITS} map with predefined temperature unit definitions.
     * Each entry in the map represents a temperature unit and its corresponding symbol.
     * <p>
     * This method is responsible for initializing the {@code TEMPERATURE_UNITS} map
     * with key-value pairs where each key is the name of a temperature unit
     * (e.g., "celsius", "fahrenheit", "kelvin"), and the value is a nested map
     * containing only "symbol".
     */
    private static void setTemperatureUnits() {
        TEMPERATURE_UNITS.put("celsius", Map.of("symbol", "°C"));
        TEMPERATURE_UNITS.put("fahrenheit", Map.of("symbol", "°F"));
        TEMPERATURE_UNITS.put("kelvin", Map.of("symbol", "K"));
    }

    /**
     * Retrieves a map containing predefined temperature units with their respective symbols.
     * Each key in the map represents the name of a temperature unit (e.g., "celsius", "fahrenheit", "kelvin"),
     * and the value is a nested map that includes "symbol".
     *
     * @return a map of temperature unit names mapped to details about their symbols
     */
    public static Map<String, Map<String, Object>> getTemperatureUnits() {
        setTemperatureUnits();
        return TEMPERATURE_UNITS;
    }
}
