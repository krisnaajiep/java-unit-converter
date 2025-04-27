package com.krisnaajiep.unitconverter.service;

/*
IntelliJ IDEA 2025.1 (Ultimate Edition)
Build #IU-251.23774.435, built on April 14, 2025
@Author krisna a.k.a. Krisna Ajie
Java Developer
Created on 27/04/25 04.26
@Last Modified 27/04/25 04.26
Version 1.0
*/

/**
 * The {@code Measurement} enum defines types of measurements that can be represented in the application.
 * The supported measurement types include LENGTH, WEIGHT, and TEMPERATURE.
 */
enum Measurement {
    LENGTH, WEIGHT, TEMPERATURE;

    /**
     * Converts a string representation of a measurement type into a Measurement enum constant.
     * The input string is case-insensitive and is converted to uppercase before matching.
     *
     * @param measurement the string representation of the measurement type
     * @return the corresponding Measurement enum constant
     * @throws IllegalArgumentException if the provided string does not match any enum constant
     * @throws NullPointerException if the provided string is null
     */
    static Measurement from(String measurement) {
        return valueOf(measurement.toUpperCase());
    }
}
