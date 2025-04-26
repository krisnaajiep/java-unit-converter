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
    private static final int SCALE = 12;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private static final BigDecimal FAHRENHEIT_RATIO = BigDecimal.valueOf(9.0/5.0);
    private static final BigDecimal FAHRENHEIT_OFFSET = BigDecimal.valueOf(32.0);
    private static final BigDecimal KELVIN_OFFSET = BigDecimal.valueOf(273.15);

    public Set<String> getUnits(String measurement) {
        try {
            return switch (Measurement.from(measurement)) {
                case LENGTH -> ConversionMap.getLengthUnits().keySet();
                case WEIGHT -> ConversionMap.getWeightUnits().keySet();
                case TEMPERATURE -> ConversionMap.getTemperatureUnits().keySet();
            };
        } catch (IllegalArgumentException e) {
            return Set.of();
        }
    }

    public Map<String, String> convert(String measurement, String from, String to, String value) {
        try {
            return switch (Measurement.from(measurement)) {
                case LENGTH -> convertLength(from, to, value);
                case WEIGHT -> convertWeight(from, to, value);
                case TEMPERATURE -> convertTemperature(from, to, value);
            };
        } catch (IllegalArgumentException e) {
            return Map.of();
        }
    }

    private UnitInfo getUnitInfo(Map<String, Map <String, Object>> units, String unit) {
        return UnitInfo.from(units.get(unit));
    }

    private Map<String, String> convertLength(String from, String to, String value) {
        var fromUnit = getUnitInfo(ConversionMap.getLengthUnits(), from);
        var toUnit = getUnitInfo(ConversionMap.getLengthUnits(), to);

        return calculateConversion(fromUnit.factor(), toUnit.factor(), fromUnit.symbol(), toUnit.symbol(), value);
    }

    private Map<String, String> convertWeight(String from, String to, String value) {
        var fromUnit = getUnitInfo(ConversionMap.getWeightUnits(), from);
        var toUnit = getUnitInfo(ConversionMap.getWeightUnits(), to);

        return calculateConversion(fromUnit.factor(), toUnit.factor(), fromUnit.symbol(), toUnit.symbol(), value);
    }

    private Map<String, String> calculateConversion(double fromFactor, double toFactor,
                                                    String fromSymbol, String toSymbol,
                                                    String value) {
        var result = new BigDecimal(value)
                .multiply(BigDecimal.valueOf(fromFactor))
                .divide(BigDecimal.valueOf(toFactor), SCALE, ROUNDING_MODE)
                .stripTrailingZeros();

        return new ConversionResult(value + fromSymbol, result + toSymbol).toMap();
    }

    private Map<String, String> convertTemperature(String from, String to, String value) {
      var fromUnit = getUnitInfo(ConversionMap.getTemperatureUnits(), from);
      var toUnit = getUnitInfo(ConversionMap.getTemperatureUnits(), to);
      var input = new BigDecimal(value);

      var result = switch (from) {
          case "celsius" -> convertFromCelsius(to, input);
          case "fahrenheit" -> convertFromFahrenheit(to, input);
          case "kelvin" -> convertFromKelvin(to, input);
          default -> input;
      };

      return new ConversionResult(value + fromUnit.symbol(), result + toUnit.symbol()).toMap();
    }

    private BigDecimal convertFromCelsius(String to, BigDecimal value) {
        return switch (to) {
            case "fahrenheit" -> value.multiply(FAHRENHEIT_RATIO).add(FAHRENHEIT_OFFSET);
            case "kelvin" -> value.add(KELVIN_OFFSET);
            default -> value;
        };
    }

    private BigDecimal convertFromFahrenheit(String to, BigDecimal value) {
        var celsius = value.subtract(FAHRENHEIT_OFFSET).divide(FAHRENHEIT_RATIO, SCALE, ROUNDING_MODE);

        return switch (to) {
            case "celsius" -> celsius;
            case "kelvin" -> celsius.add(KELVIN_OFFSET);
            default -> value;
        };
    }

    private BigDecimal convertFromKelvin(String to, BigDecimal value) {
        var celsius = value.subtract(KELVIN_OFFSET);

        return switch (to) {
            case "celsius" -> celsius;
            case "fahrenheit" -> celsius.multiply(FAHRENHEIT_RATIO).add(FAHRENHEIT_OFFSET);
            default -> value;
        };
    }
}
