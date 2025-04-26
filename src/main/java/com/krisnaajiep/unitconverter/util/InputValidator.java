package com.krisnaajiep.unitconverter.util;

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

public class InputValidator {
    private static final ThreadLocal<Map<String, String>> errors = ThreadLocal.withInitial(HashMap::new);

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

    private static void validate(Object input, String rule, String name) {
        switch (rule) {
            case "required" -> validateRequired(input, name);
            case "number" -> validateNumber(input, name);
            default -> errors.get().put(name, "Invalid rule: " + rule);
        }
    }

    private static void validateRequired(Object input, String name) {
        if (input == null || input.toString().isBlank()) {
            errors.get().put(name, "The " + name + " is required.");
        }
    }

    private static void validateNumber(Object input, String name) {
        try {
            new BigDecimal(input.toString());
        } catch (NumberFormatException e) {
            errors.get().put(name, "Value must be a number.");
        }
    }

    public static Map<String, String> getErrors() {
        return new HashMap<>(errors.get());
    }

    public static void clearErrors() {
        errors.get().clear();
        errors.remove();
    }
}
