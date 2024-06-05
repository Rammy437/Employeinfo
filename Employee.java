package org.employeeinfo.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank(message = "First name is mandatory")
    @Length(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Length(max = 100)
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Length(max = 200)
    private String email;

    @ElementCollection
    @CollectionTable(name = "employee_phone_numbers", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "phone_number")
    @NotNull(message = "Phone number is mandatory")
    private List<@NotBlank(message = "Phone number cannot be blank") String> phoneNumbers;

    @NotNull(message = "Date of joining is mandatory")
    @Past(message = "Date of joining must be in the past")
    private LocalDate doj;

    @NotNull(message = "Salary is mandatory")
    @Positive(message = "Salary must be positive")
    private Double salary;

    public Double calculateYearlySalary(LocalDate currentFinancialYearStart) {
        LocalDate joiningDate = this.doj;
        if (joiningDate.isBefore(currentFinancialYearStart)) {
            joiningDate = currentFinancialYearStart;
        }

        long monthsWorked = ChronoUnit.MONTHS.between(joiningDate.withDayOfMonth(1), LocalDate.now().withDayOfMonth(1));
        return this.salary * monthsWorked;
    }
}


