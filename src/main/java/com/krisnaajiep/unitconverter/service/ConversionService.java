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

/**
 * The {@code ConversionService} class provides conversion functionalities for different types of measurements,
 * including length, weight, and temperature. It uses predefined conversion maps and performs
 * precise calculations with rounding to ensure accuracy.
 */
public class ConversionService {
    /**
     * A constant representing the scale to be used in precise calculations,
     * such as division or rounding operations, within the unit conversion process.
     * This scale ensures accurate representation of decimal values by defining
     * the number of digits to the right of the decimal point during calculations.
     */
    private static final int SCALE = 12;

    /**
     * Defines the rounding behavior for numerical operations in the conversion process.
     * This constant is set to {@code RoundingMode.HALF_UP}, which rounds towards
     * the nearest neighbor unless both neighbors are equidistant, in which case it rounds up.
     */
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * Represents the conversion ratio used when converting temperatures from Celsius to Fahrenheit.
     * Specifically, this ratio corresponds to 9/5, which is a key part of the formula:
     * Fahrenheit = (Celsius * 9/5) + 32.
     */
    private static final BigDecimal FAHRENHEIT_RATIO = BigDecimal.valueOf(9.0/5.0);

    /**
     * Represents the offset used in Fahrenheit temperature conversion.
     * Specifically, this offset is added or subtracted during conversion
     * between Celsius and Fahrenheit scales as part of the formula.
     * It holds the constant value of 32.0, which is the difference in the
     * freezing points of water in Fahrenheit (32°F) and Celsius (0°C).
     */
    private static final BigDecimal FAHRENHEIT_OFFSET = BigDecimal.valueOf(32.0);

    /**
     * Represents the numeric offset used to convert temperatures
     * between Celsius and Kelvin. The constant value 273.15 is added to a temperature in Celsius
     * to convert it to Kelvin, or subtracted from a temperature in Kelvin
     * to convert it to Celsius.
     */
    private static final BigDecimal KELVIN_OFFSET = BigDecimal.valueOf(273.15);

    /**
     * Retrieves the available units for a given measurement type.
     * If the provided measurement type is invalid, an empty set is returned.
     *
     * @param measurement the type of measurement (e.g., "LENGTH", "WEIGHT", "TEMPERATURE")
     * @return a set of unit names for the specified measurement type, or an empty set if the type is invalid
     */
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

    /**
     * Converts a value from one unit to another within a specified measurement type.
     * The method supports "length", "weight", and "temperature" conversions.
     * If the input measurement type is invalid, an empty map is returned.
     *
     * @param measurement the type of measurement to perform the conversion for
     *                     (e.g., "length", "weight", "temperature")
     * @param from the source unit of the measurement (e.g., "meter", "gram", "celsius")
     * @param to the target unit of the measurement (e.g., "kilometer", "kilogram", "fahrenheit")
     * @param value the numeric value to be converted, represented as a string
     * @return a map containing the conversion result with "from" and "to" keys,
     *         or an empty map if the conversion could not be performed
     */
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

    /**
     * Retrieves unit information for a specific unit from a given map of units.
     * This method extracts factor and symbol details for the unit and returns it
     * as a {@code UnitInfo} object.
     *
     * @param units a map containing unit configurations, where the key is the
     *              unit name and the value is another map with unit properties
     *              such as "factor" and "symbol"
     * @param unit the name of the unit for which information is to be retrieved
     * @return a {@code UnitInfo} object containing the factor and symbol of the
     *         specified unit, or {@code null} if the unit does not exist in the map
     */
    private UnitInfo getUnitInfo(Map<String, Map <String, Object>> units, String unit) {
        return UnitInfo.from(units.get(unit));
    }

    /**
     * Converts a length value from one unit to another. This method retrieves
     * unit information for the source and target units, including conversion
     * factors and symbols, then performs the conversion and returns the result
     * as a map containing the "from" and "to" values.
     *
     * @param from the source unit of the length measurement (e.g., "meter")
     * @param to the target unit of the length measurement (e.g., "kilometer")
     * @param value the numeric value to be converted, represented as a string
     * @return a map containing the conversion result with keys "from" and "to",
     *         or an empty map if the conversion could not be performed
     */
    private Map<String, String> convertLength(String from, String to, String value) {
        var fromUnit = getUnitInfo(ConversionMap.getLengthUnits(), from);
        var toUnit = getUnitInfo(ConversionMap.getLengthUnits(), to);

        return calculateConversion(fromUnit.factor(), toUnit.factor(), fromUnit.symbol(), toUnit.symbol(), value);
    }

    /**
     * Converts a weight value from one unit to another. This method retrieves the
     * unit information for both source and target units, including their conversion
     * factors and symbols, performs the conversion, and returns the result.
     *
     * @param from the source unit of the weight measurement (e.g., "gram", "kilogram")
     * @param to the target unit of the weight measurement (e.g., "ounce", "pound")
     * @param value the numeric value to be converted, represented as a string
     * @return a map containing the conversion result with keys "from" and "to",
     *         or an empty map if the conversion could not be performed
     */
    private Map<String, String> convertWeight(String from, String to, String value) {
        var fromUnit = getUnitInfo(ConversionMap.getWeightUnits(), from);
        var toUnit = getUnitInfo(ConversionMap.getWeightUnits(), to);

        return calculateConversion(fromUnit.factor(), toUnit.factor(), fromUnit.symbol(), toUnit.symbol(), value);
    }

    /**
     * Performs the conversion of a value from one unit to another based on the provided conversion factors
     * and unit symbols. The result is formatted as a map containing the original and converted values.
     *
     * @param fromFactor the conversion factor for the source unit
     * @param toFactor the conversion factor for the target unit
     * @param fromSymbol the symbol of the source unit
     * @param toSymbol the symbol of the target unit
     * @param value the numeric value to be converted, represented as a string
     * @return a map containing the conversion result with keys "from" (formatted original value with source unit symbol)
     *         and "to" (formatted converted value with target unit symbol)
     */
    private Map<String, String> calculateConversion(double fromFactor, double toFactor,
                                                    String fromSymbol, String toSymbol,
                                                    String value) {
        var result = new BigDecimal(value)
                .multiply(BigDecimal.valueOf(fromFactor))
                .divide(BigDecimal.valueOf(toFactor), SCALE, ROUNDING_MODE)
                .stripTrailingZeros()
                .toPlainString();

        return new ConversionResult(value + fromSymbol, result + toSymbol).toMap();
    }

    /**
     * Converts a temperature value from one unit to another and returns the result as a map.
     * This method supports conversions between Celsius, Fahrenheit, and Kelvin.
     *
     * @param from the source temperature unit (e.g., "celsius", "fahrenheit", "kelvin")
     * @param to the target temperature unit (e.g., "celsius", "fahrenheit", "kelvin")
     * @param value the numeric temperature value to be converted, represented as a string
     * @return a map containing the conversion result, with the original value and unit under the "from" key
     *         and the converted value and unit under the "to" key; or an empty map if conversion fails
     */
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

    /**
     * Converts a temperature value in Celsius to a specified target unit.
     *
     * @param to the target temperature unit to convert to (e.g., "fahrenheit", "kelvin")
     * @param value the temperature value in Celsius to be converted, represented as a {@code BigDecimal}
     * @return the converted temperature value in the target unit as a {@code BigDecimal}.
     *         Returns the input value if the target unit is not recognized.
     */
    private BigDecimal convertFromCelsius(String to, BigDecimal value) {
        return switch (to) {
            case "fahrenheit" -> value.multiply(FAHRENHEIT_RATIO).add(FAHRENHEIT_OFFSET);
            case "kelvin" -> value.add(KELVIN_OFFSET);
            default -> value;
        };
    }

    /**
     * Converts a temperature value from Fahrenheit to the specified target unit.
     * Supports conversion to Celsius and Kelvin. If the target unit is not recognized,
     * the input value is returned unchanged.
     *
     * @param to the target temperature unit to convert to (e.g., "celsius", "kelvin")
     * @param value the temperature value in Fahrenheit to be converted,
     *              represented as a {@code BigDecimal}
     * @return the converted temperature value in the target unit as a {@code BigDecimal}.
     *         Returns the input value if the target unit is not recognized.
     */
    private BigDecimal convertFromFahrenheit(String to, BigDecimal value) {
        var celsius = value.subtract(FAHRENHEIT_OFFSET).divide(FAHRENHEIT_RATIO, SCALE, ROUNDING_MODE);

        return switch (to) {
            case "celsius" -> celsius;
            case "kelvin" -> celsius.add(KELVIN_OFFSET);
            default -> value;
        };
    }

    /**
     * Converts a temperature value from Kelvin to the specified target unit.
     * Supports conversion to Celsius and Fahrenheit. If the target unit is not recognized,
     * the input value in Kelvin is returned unchanged.
     *
     * @param to the target temperature unit to convert to (e.g., "celsius", "fahrenheit")
     * @param value the temperature value in Kelvin to be converted, represented as a {@code BigDecimal}
     * @return the converted temperature value in the target unit as a {@code BigDecimal}.
     *         Returns the input value if the target unit is not recognized.
     */
    private BigDecimal convertFromKelvin(String to, BigDecimal value) {
        var celsius = value.subtract(KELVIN_OFFSET);

        return switch (to) {
            case "celsius" -> celsius;
            case "fahrenheit" -> celsius.multiply(FAHRENHEIT_RATIO).add(FAHRENHEIT_OFFSET);
            default -> value;
        };
    }
}
