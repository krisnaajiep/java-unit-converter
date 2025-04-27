package com.krisnaajiep.unitconverter.service;

import java.util.Map;

/**
 * The {@code UnitInfo} record represents a unit of measurement with its conversion factor and symbol.
 * This class is immutable and uses Java's record feature for compact representation.
 */
record UnitInfo(double factor, String symbol) {
    /**
     * Creates a UnitInfo instance from a map containing unit data.
     * The map should include a "factor" key representing the conversion factor (as a double)
     * and a "symbol" key representing the unit's symbol as a string.
     * If the "factor" key is absent, it defaults to 0.0.
     *
     * @param data a map containing keys "factor" and "symbol" for the unit information.
     *             The "factor" value is expected to be of type double, and "symbol" is expected to be a string.
     * @return a UnitInfo instance initialized with the provided factor and symbol.
     */
    static UnitInfo from(Map<String, Object> data) {
        return new UnitInfo(
                data.containsKey("factor") ? (double) data.get("factor") : 0.0,
                data.get("symbol").toString()
        );
    }
}
