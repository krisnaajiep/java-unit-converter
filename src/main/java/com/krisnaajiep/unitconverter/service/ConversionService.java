package com.krisnaajiep.unitconverter.service;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 26/04/25 03.53
@Last Modified 26/04/25 03.53
Version 1.0
*/

import com.krisnaajiep.unitconverter.util.ConversionMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

public class ConversionService {
    public Set<String> getUnits(String measurement) {
        return switch (measurement.toLowerCase()) {
            case "length" -> ConversionMap.getLengthUnits().keySet();
            case "weight" -> ConversionMap.getWeightUnits().keySet();
            case "temperature" -> ConversionMap.getTemperatureUnits().keySet();
            default -> Set.of();
        };
    }

    public Map<String, String> convert(String measurement, String from, String to, String value) {
        return switch (measurement.toLowerCase()) {
            case "length" -> convertLength(from, to, value);
            case "weight" -> convertWeight(from, to, value);
            case "temperature" -> convertTemperature(from, to, value);
            default -> Map.of();
        };
    }

    private Map<String, String> convertLength(String from, String to, String value) {
        double fromFactor = (double) ConversionMap.getLengthUnits().get(from).get("factor");
        double toFactor = (double) ConversionMap.getLengthUnits().get(to).get("factor");

        String fromSymbol = ConversionMap.getLengthUnits().get(from).get("symbol").toString();
        String toSymbol = ConversionMap.getLengthUnits().get(to).get("symbol").toString();

        return calculateConversion(fromFactor, toFactor, fromSymbol, toSymbol, value);
    }

    private Map<String, String> convertWeight(String from, String to, String value) {
        double fromFactor = (double) ConversionMap.getWeightUnits().get(from).get("factor");
        double toFactor = (double) ConversionMap.getWeightUnits().get(to).get("factor");

        String fromSymbol = ConversionMap.getWeightUnits().get(from).get("symbol").toString();
        String toSymbol = ConversionMap.getWeightUnits().get(to).get("symbol").toString();

        return calculateConversion(fromFactor, toFactor, fromSymbol, toSymbol, value);
    }

    private Map<String, String> calculateConversion(double fromFactor, double toFactor,
                                                    String fromSymbol, String toSymbol,
                                                    String value) {
        BigDecimal bigDecimalValue = new BigDecimal(value);

        BigDecimal result = bigDecimalValue
                .multiply(BigDecimal.valueOf(fromFactor))
                .divide(BigDecimal.valueOf(toFactor), 12, RoundingMode.HALF_UP);

        return Map.of("from", value + fromSymbol, "to", result.stripTrailingZeros() + toSymbol);
    }

    private Map<String, String> convertTemperature(String from, String to, String value) {
        String fromSymbol = ConversionMap.getTemperatureUnits().get(from).get("symbol").toString();
        String toSymbol = ConversionMap.getTemperatureUnits().get(to).get("symbol").toString();

        BigDecimal bigDecimalValue = new BigDecimal(value);

        BigDecimal result;

        switch (from) {
            case "celsius" -> result = switch (to) {
                case "fahrenheit" -> bigDecimalValue
                        .multiply(BigDecimal.valueOf(9.0))
                        .divide(BigDecimal.valueOf(5.0), 12, RoundingMode.HALF_UP)
                        .add(BigDecimal.valueOf(32.0));
                case "kelvin" -> bigDecimalValue
                        .add(BigDecimal.valueOf(273.15));
                default -> bigDecimalValue;
            };
            case "fahrenheit" -> {
                final BigDecimal multiply = (bigDecimalValue.subtract(BigDecimal.valueOf(32.0)))
                        .multiply(BigDecimal.valueOf(5.0));

                result = switch (to) {
                    case "celsius" -> multiply
                            .divide(BigDecimal.valueOf(9.0), 12, RoundingMode.HALF_UP);
                    case "kelvin" -> multiply
                            .divide(BigDecimal.valueOf(9.0), 12, RoundingMode.HALF_UP)
                            .add(BigDecimal.valueOf(273.15));
                    default -> multiply;
                };
            }
            case "kelvin" -> result = switch (to) {
                case "celsius" -> bigDecimalValue
                        .subtract(BigDecimal.valueOf(273.15));
                case "fahrenheit" -> bigDecimalValue
                        .subtract(BigDecimal.valueOf(273.15))
                        .multiply(BigDecimal.valueOf(9.0))
                        .divide(BigDecimal.valueOf(5.0), 12, RoundingMode.HALF_UP)
                        .add(BigDecimal.valueOf(32.0));
                default -> bigDecimalValue;
            };
            default -> result = bigDecimalValue;
        }

        return Map.of("from", value + fromSymbol, "to", result.stripTrailingZeros() + toSymbol);
    }
}
