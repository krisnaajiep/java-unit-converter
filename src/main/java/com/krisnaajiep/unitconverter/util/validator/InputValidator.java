package com.krisnaajiep.unitconverter.util.validator;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 27/04/25 00.47
@Last Modified 27/04/25 00.47
Version 1.0
*/

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code InputValidator} class provides utilities for validating input data based on
 * specified rules. It manages a thread-local map of validation errors that can be
 * queried and cleared as needed.
 */
public class InputValidator {
    /**
     * A thread-local variable to store validation errors in the form of a map.
     * Each thread maintains its own isolated instance of the {@code Map<String, String>},
     * where the keys are the names of inputs and the values are error messages
     * associated with those inputs.
     */
    private static final ThreadLocal<Map<String, String>> errors = ThreadLocal.withInitial(HashMap::new);

    /**
     * Applies a set of validation rules to specified input values. For each input,
     * the corresponding validation rules are processed, and any validation errors
     * are stored in a thread-local error map.
     *
     * @param inputs a {@code Map} where the keys are input field names and the values
     *               are the corresponding input values to be validated
     * @param rules a {@code Map} where the keys are input field names, and the values
     *              are arrays of validation rules that should be applied to the corresponding input
     */
    public static void setRules(Map<String, Object> inputs, Map<String, String[]> rules) {
        rules.forEach((name, ruleSet) -> {
            Object input = inputs.get(name);

            for (String rule : ruleSet) {
                if (!errors.get().containsKey(name)) {
                    validate(input, rule, name);
                }
            }
        });
    }

    /**
     * Validates the provided input based on the specified rule and stores any validation
     * errors in a thread-local error map.
     *
     * @param input the input value to be validated
     * @param rule the validation rule to apply, such as "REQUIRED" or "NUMBER"
     * @param name the name of the input field, used for error identification
     */
    private static void validate(Object input, String rule, String name) {
        try {
            switch (Rule.of(rule)) {
                case REQUIRED -> validateRequired(input, name);
                case NUMBER -> validateNumber(input, name);
            }
        } catch (IllegalArgumentException e) {
            errors.get().put(name, e.getMessage());
        }
    }

    /**
     * Validates that the specified input is provided and not blank. If the input is null
     * or blank, an error message is recorded in a thread-local error map.
     *
     * @param input the input value to be validated; this can be any object
     * @param name the name of the input field, used for reference in the error message
     */
    private static void validateRequired(Object input, String name) {
        if (input == null || input.toString().isBlank()) {
            errors.get().put(name, "The " + name + " is required.");
        }
    }

    /**
     * Validates that the specified input is a numeric value. If the input cannot
     * be parsed as a number, an error message is recorded in a thread-local
     * error map.
     *
     * @param input the input value to be validated; this can be any object, but will
     *              be converted to a string for numeric parsing
     * @param name the name of the input field, used as the key for identifying
     *             validation errors in the error map
     */
    private static void validateNumber(Object input, String name) {
        try {
            new BigDecimal(input.toString());
        } catch (NumberFormatException e) {
            errors.get().put(name, "Value must be a number.");
        }
    }

    /**
     * Retrieves a map of validation errors stored in a thread-local context.
     * The map keys represent the field names, and the corresponding values
     * are the error messages associated with the validation of those fields.
     *
     * @return a Map containing validation errors, with field names as keys
     *         and error messages as values. If no errors exist, an empty map
     *         is returned.
     */
    public static Map<String, String> getErrors() {
        return new HashMap<>(errors.get());
    }

    /**
     * Clears all validation errors stored in the thread-local error map.
     * This method ensures that any previously recorded errors are removed,
     * releasing resources tied to the current thread's error context.
     */
    public static void clearErrors() {
        errors.get().clear();
        errors.remove();
    }
}
