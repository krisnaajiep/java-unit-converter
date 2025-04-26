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
    public Set<String> getUnits(String unit) {
        if (unit.equals("length")) {
            return ConversionMap.getLengthUnits().keySet();
        } else if (unit.equals("weight")) {
            return ConversionMap.getWeightUnits().keySet();
        } else {
            return Set.of();
        }
    }

    public Map<String, String> convertLength(String from, String to, String value) {
        double fromFactor = (double) ConversionMap.getLengthUnits().get(from).get("factor");
        double toFactor = (double) ConversionMap.getLengthUnits().get(to).get("factor");

        String fromSymbol = ConversionMap.getLengthUnits().get(from).get("symbol").toString();
        String toSymbol = ConversionMap.getLengthUnits().get(to).get("symbol").toString();

        return calculateConversion(fromFactor, toFactor, fromSymbol, toSymbol, value);
    }

    public Map<String, String> convertWeight(String from, String to, String value) {
        double fromFactor = (double) ConversionMap.getWeightUnits().get(from).get("factor");
        double toFactor = (double) ConversionMap.getWeightUnits().get(to).get("factor");

        String fromSymbol = ConversionMap.getWeightUnits().get(from).get("symbol").toString();
        String toSymbol = ConversionMap.getWeightUnits().get(to).get("symbol").toString();

        return calculateConversion(fromFactor, toFactor, fromSymbol, toSymbol, value);
    }

    private Map<String, String> calculateConversion(
            double fromFactor, double toFactor, String fromSymbol, String toSymbol, String value) {
        BigDecimal bigDecimalValue = new BigDecimal(value);

        BigDecimal result = bigDecimalValue
                .multiply(BigDecimal.valueOf(fromFactor))
                .divide(BigDecimal.valueOf(toFactor), 12, RoundingMode.HALF_UP);

        return Map.of("from", value + fromSymbol, "to", result.stripTrailingZeros() + toSymbol);
    }
}
