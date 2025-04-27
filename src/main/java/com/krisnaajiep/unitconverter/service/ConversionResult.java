package com.krisnaajiep.unitconverter.service;

import java.util.Map;

/**
 * The  {@code ConversionResult} record represents the result of a conversion operation, including the source and target units.
 * This class is immutable and uses Java's record feature for compactness and simplicity.
 */
record ConversionResult(String from, String to) {
    /**
     * Converts the current object state into a map representation where the keys are "from" and "to",
     * corresponding to the source and target units respectively.
     *
     * @return a map containing the "from" and "to" entries representing the source and target units.
     */
    Map<String, String> toMap() {
        return Map.of("from", from, "to", to);
    }
}
