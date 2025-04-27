package com.krisnaajiep.unitconverter.util.validator;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 27/04/25 04.50
@Last Modified 27/04/25 04.50
Version 1.0
*/

/**
 * The {@code Rule} enum defines a set of validation rules that can be used for
 * input validation. Each enum constant represents a specific validation rule.
 */
enum Rule {
    REQUIRED,
    NUMBER;

    /**
     * Converts the provided string into a corresponding {@code Rule} enum constant.
     * The input string is converted to uppercase to ensure case-insensitive matching.
     *
     * @param rule the name of the validation rule to be converted. It should match one
     *             of the predefined {@code Rule} constants (e.g., "REQUIRED", "NUMBER").
     * @return the {@code Rule} enum constant representing the provided rule name.
     * @throws IllegalArgumentException if the provided string does not match any
     *                                  existing {@code Rule} constant.
     */
    static Rule of(String rule) {
        return valueOf(rule.toUpperCase());
    }
}
