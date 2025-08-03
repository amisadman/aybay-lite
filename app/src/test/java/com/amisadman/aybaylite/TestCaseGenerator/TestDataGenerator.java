package com.amisadman.aybaylite.TestCaseGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestDataGenerator {
    private static final int MINIMUM = 1;
    private static final int MAXIMUM = (int) 1e9;
    private static final int NOMINAL = 500_000_000;

    private static final String CSV_HEADER = "amount,reason,range_type";
    private static final String[] REASONS = {
            "Salary", "Bonus", "Freelance", "Investment", "Side Hustle"
    };

    public static void generateMinToNominal(String filename, int numRecords) {
        generateData(filename, numRecords, MINIMUM, NOMINAL, "min-nominal");
    }

    public static void generateNominalToMax(String filename, int numRecords) {
        generateData(filename, numRecords, NOMINAL, MAXIMUM, "nominal-max");
    }

    public static void generateCombined(String filename, int numMinToNominal, int numNominalToMax) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(CSV_HEADER + "\n");
            Random random = new Random();

            for (int i = 0; i < numMinToNominal; i++) {
                double amount = generateAmount(random, MINIMUM, NOMINAL);
                writer.write(formatRecord(amount, "min-nominal"));
            }

            for (int i = 0; i < numNominalToMax; i++) {
                double amount = generateAmount(random, NOMINAL, MAXIMUM);
                writer.write(formatRecord(amount, "nominal-max"));
            }

            System.out.printf("Generated %d min→nominal and %d nominal→max records in %s\n",
                    numMinToNominal, numNominalToMax, filename);
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static void generateData(String filename, int numRecords,
                                     double min, double max, String rangeType) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(CSV_HEADER + "\n");
            Random random = new Random();

            for (int i = 0; i < numRecords; i++) {
                double amount = generateAmount(random, min, max);
                writer.write(formatRecord(amount, rangeType));
            }

            System.out.printf("Generated %d records (%s range) in %s\n",
                    numRecords, rangeType, filename);
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static double generateAmount(Random random, double min, double max) {
        return Math.round(min + (max - min) * random.nextDouble() * 100) / 100.0;
    }

    private static String formatRecord(double amount, String rangeType) {
        Random random = new Random();
        String reason = REASONS[random.nextInt(REASONS.length)];
        return String.format("%.2f,%s,%s\n", amount, reason, rangeType);
    }
}
