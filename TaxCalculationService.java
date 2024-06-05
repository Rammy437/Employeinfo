package org.employeeinfo.demo.service;

import org.springframework.stereotype.Service;

@Service
public class TaxCalculationService {

    public double calculateTax(double yearlySalary) {
        double tax = 0;

        if (yearlySalary <= 250000) {
            tax = 0;
        } else if (yearlySalary <= 500000) {
            tax = (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            tax = (250000 * 0.05) + (yearlySalary - 500000) * 0.10;
        } else {
            tax = (250000 * 0.05) + (500000 * 0.10) + (yearlySalary - 1000000) * 0.20;
        }

        return tax;
    }

    public double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        } else {
            return 0;
        }
    }
}

