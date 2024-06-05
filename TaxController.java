package org.employeeinfo.demo.controller;

import org.employeeinfo.demo.EmployeeTaxDTO;
import org.employeeinfo.demo.Entity.Employee;
import org.employeeinfo.demo.service.EmployeeService;
import org.employeeinfo.demo.service.TaxCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tax")
public class TaxController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaxCalculationService taxCalculationService;

    @GetMapping
    public ResponseEntity<List<EmployeeTaxDTO>> getEmployeeTaxDetails(@RequestParam int year) {
        LocalDate financialYearStart = LocalDate.of(year, Month.APRIL, 1);
        List<Employee> employees = employeeService.getAllEmployees();

        List<EmployeeTaxDTO> employeeTaxDetails = employees.stream().map(employee -> {
            Double yearlySalary = employee.calculateYearlySalary(financialYearStart);
            Double tax = taxCalculationService.calculateTax(yearlySalary);
            Double cess = taxCalculationService.calculateCess(yearlySalary);

            return new EmployeeTaxDTO(
                    employee.getEmployeeId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    yearlySalary,
                    tax,
                    cess
            );
        }).collect(Collectors.toList());

        return new ResponseEntity<>(employeeTaxDetails, HttpStatus.OK);
    }
}

