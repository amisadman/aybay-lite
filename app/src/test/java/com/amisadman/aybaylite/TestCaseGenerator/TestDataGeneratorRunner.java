package com.amisadman.aybaylite.TestCaseGenerator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class TestDataGeneratorRunner {

    @Test
    void generateTestCSVs() {
        String resourceDir = Paths.get("src", "test", "resources").toString();

        TestDataGenerator.generateMinToNominal(resourceDir + "/min_to_nominal.csv", 500);
        TestDataGenerator.generateNominalToMax(resourceDir + "/nominal_to_max.csv", 500);
        TestDataGenerator.generateCombined(resourceDir + "/combined_test_data.csv", 250, 250);
    }
}
