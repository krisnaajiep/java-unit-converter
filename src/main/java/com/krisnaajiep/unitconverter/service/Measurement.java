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

enum Measurement {
    LENGTH, WEIGHT, TEMPERATURE;

    static Measurement from(String measurement) {
        return valueOf(measurement.toUpperCase());
    }
}
