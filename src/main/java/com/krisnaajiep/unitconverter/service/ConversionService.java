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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversionService {
    public List<String> getUnits(String unit) {
        return ConversionMap.getLengthUnits().keySet().stream().toList();
    }

    public Map<String, String> convertLength(String from, String to, String value) {
        double fromFactor = (double) ConversionMap.getLengthUnits().get(from).get("factor");
        double toFactor = (double) ConversionMap.getLengthUnits().get(to).get("factor");

        String fromSymbol = ConversionMap.getLengthUnits().get(from).get("symbol").toString();
        String toSymbol = ConversionMap.getLengthUnits().get(to).get("symbol").toString();

        BigDecimal bigDecimalValue = new BigDecimal(value);

        BigDecimal result = bigDecimalValue
                .multiply(BigDecimal.valueOf(fromFactor))
                .divide(BigDecimal.valueOf(toFactor), 12, RoundingMode.HALF_UP);

        Map<String, String> map = new HashMap<>();
        map.put("from", value + fromSymbol);
        map.put("to", result.stripTrailingZeros() + toSymbol);

        return map;
    }
}
