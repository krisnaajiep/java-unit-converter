package com.krisnaajiep.unitconverter.service;

import java.util.Map;

record ConversionResult(String from, String to) {
    Map<String, String> toMap() {
        return Map.of("from", from, "to", to);
    }
}
