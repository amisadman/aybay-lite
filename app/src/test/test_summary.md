# 🧪 Test Summary
<p align="center">
  <img src="https://github.com/amisadman/aybay-lite/blob/main/ExternalResources/aybay-animation.gif"/>
</p>

<h1 align="center">AyBay Lite</h1>

<p align="center"><i>
A powerful and lightweight Android application for tracking income, expenses, budgets, savings, loans, and more.
</i></p>

---


## Purpose & Types of Testing

In this project, our goal was to ensure robust, isolated, and fast testing of core logic components using **local unit testing**.<br><br> Android supports two main types of testing:

- **Instrumented Testing:** Runs on a device or emulator. Used for components tightly coupled with the Android framework (e.g., `Activity`, `Fragment`, `Lifecycle`).
- **Local Unit Testing:** Runs on the JVM. Fast, framework-independent, and suitable for business logic testing.

>  **This project uses Local Unit Testing (JVM).**

---

## Technologies Used

| Technology | Version  |
|------------|----------|
| JUnit      | 5.10.2   |
| Mockito    | 5.11.0   |

---

## Branch Structure

| Branch Name | Author            | Purpose                                                |
|-------------|-------------------|--------------------------------------------------------|
| `phoenix`   | Sadman Islam      | Unit testing and related logic      |
| `clove`     | Md. Tahmid Rahman  | Unit testing and related logic     |
| `nebula`    | Sadman & Tahmid   | Bug-fix and cleanup               |

---

## Test Summary

| Class Name | Line No. | Test Description                                      | Author           |
|------------------------|----------|-------------------------------------------------------|------------------|
| [`AddIncomeHelperTest`](./java/com/amisadman/aybaylite/Controllers/AddIncomeHelperTest.java)| Majority Contribution | Test Add data and Update data with Parameterized test using **Value Source**, **CSV Source**, **CSV File Source**, **Boundary Value Checking**, **Robust Testing**, **Randomize Testing** | Sadman Islam     |
| [`AddIncomeHelperTest`](./java/com/amisadman/aybaylite/Controllers/AddIncomeHelperTest.java)| 188-228, 241-268 | Test Update data using **Method Source**, **Boundary Value Testing**, **Randomize Testing**| Md. Tahmid Rahman |
| [`AddExpenseHelperTest`](./java/com/amisadman/aybaylite/Controllers/AddExpenseHelperTest.java)| Majority Contribution | Test Add data and Update data with Parameterized test using **Value Source**, **CSV Source**, **CSV File Source**, **Boundary Value Checking**, **Robust Testing**, **Randomize Testing**| Md. Tahmid Rahman|
| [`AddExpenseHelperTest`](./java/com/amisadman/aybaylite/Controllers/AddExpenseHelperTest.java)   | 140-180, 225-233, 262-281 |Test Update data using **Method Source**, **Boundary Value Testing**, **Randomize Testing**| Sadman Islam |
| [`DashboardManagerTest`](./java/com/amisadman/aybaylite/Controllers/DashboardManagerTest.java) | | Test Data Retrival using **Mockito**, **Assert**, **Latency Checking**  | Both Authors      |
| [`TestDataGenerator.java`](./java/com/amisadman/aybaylite/TestCaseGenerator/TestDataGenerator.java) | | Generate **boundary values**, **nominal values**, and various input combinations  | Sadman Islam |
| [`TestDataGeneratorRunner.java`](./java/com/amisadman/aybaylite/TestCaseGenerator/TestDataGeneratorRunner.java) | | Generate csv files  | Sadman Islam |


---
## Random Test Case Generator for Controllers

This utility generates randomized test data in CSV format to test the controller class using JUnit csv file source parameterized tests.

It produces data that simulates real-world scenarios: **boundary values**, **nominal values**, and various input combinations — saved to `.csv` files used in tests.

---

## Generator Files

| File | Description |
|------|-------------|
| [`TestDataGenerator.java`](./java/com/amisadman/aybaylite/TestCaseGenerator/TestDataGenerator.java) | The core generator logic |
| [`TestDataGeneratorRunner.java`](./java/com/amisadman/aybaylite/TestCaseGenerator/TestDataGeneratorRunner.java) | JUnit test to trigger CSV file creation |

## How to Use the Test Case Generator

This section explains how to generate the CSV test files using the `TestDataGenerator` utility in the project.

## Step-by-Step Instructions

1. **Open** the project in **Android Studio**.

2. **Navigate to** the class: [`TestDataGeneratorRunner.java`](./java/com/amisadman/aybaylite/TestCaseGenerator/TestDataGeneratorRunner.java)

3. **Run** the `generateTestCSVs()` method. You can also customize the number of Records.
4. CSV Files Generated.


## File Structure

- `min_to_nominal.csv` → values from **1** to **500,000,000**
- `nominal_to_max.csv` → values from **500,000,000** to **1,000,000,000**
- `combined_test_data.csv` → contains both ranges

Each record has:

```csv
amount,reason,range_type
7500000.00,Salary,min-nominal
850000000.00,Freelance,nominal-max
