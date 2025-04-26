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

enum Rule {
    REQUIRED,
    NUMBER;

    static Rule of(String rule) {
        return valueOf(rule.toUpperCase());
    }
}
