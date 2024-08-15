package com.jameswhupp.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentalAgreement implements Serializable {
    private static DecimalFormat currency = new DecimalFormat("$ #,##0.00");
    private static DateTimeFormatter monthDayYearDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercentage;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public void printRentalAgreementToConsole() {
        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + toolBrand);
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Checkout date: " + checkOutDate.format(monthDayYearDateFormatter));
        System.out.println("Due date: " + dueDate.format(monthDayYearDateFormatter));
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-Discount charge: " + currency.format(preDiscountCharge.doubleValue()));
        System.out.println("Discount percentage: " + discountPercentage + "%");
        System.out.println("Discount amount: " + currency.format(discountAmount.doubleValue()));
        System.out.println("Final charge: " + currency.format(finalCharge.doubleValue()));
    }
}
