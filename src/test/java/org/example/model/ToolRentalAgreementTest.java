package org.example.model;

import org.example.service.ToolRentalService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ToolRentalAgreementTest {

    private static final ToolRentalService rentalService = new ToolRentalService();

    @BeforeAll
    static void setUp() {
        rentalService.addToolType(createToolType("Chainsaw", BigDecimal.valueOf(1.99), true, true, false));
        rentalService.addToolType(createToolType("Ladder", BigDecimal.valueOf(1.49), true, false, true));
        rentalService.addToolType(createToolType("Jackhammer", BigDecimal.valueOf(2.99), true, false, false));

        rentalService.addTool(createTool("CHNS", "Chainsaw", "Stihl"));
        rentalService.addTool(createTool("LADW", "Ladder", "Werner"));
        rentalService.addTool(createTool("JAKD", "Jackhammer", "DeWalt"));
        rentalService.addTool(createTool("JAKR", "Jackhammer", "Ridgid"));

    }


    @Test
    void test1() {
        LocalDate checkoutDate = LocalDate.of(2024, 9, 3);
        int rentalDays = 5;
        int discountPercent = 101;
        Tool tool = rentalService.getTool("JAKR");
        ToolType toolType = rentalService.getToolType(tool.getType());

        // Validate calculations
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ToolRentalAgreement(tool, toolType, rentalDays, checkoutDate, discountPercent);
        });

        String expectedMessage = "Discount percent must be between 0 and 100.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void test2() {
        LocalDate checkoutDate = LocalDate.of(2025, 7, 2);
        int rentalDays = 3;
        int discountPercent = 10;
        Tool tool = rentalService.getTool("LADW");
        ToolType toolType = rentalService.getToolType(tool.getType());
        ToolRentalAgreement agreement = new ToolRentalAgreement(tool, toolType, rentalDays, checkoutDate, discountPercent);
        agreement.printAgreement();
        // Validate due date
        assertEquals(LocalDate.of(2025, 7, 5), agreement.calculateDueDate());
        // Validate charge days
        assertEquals(2, agreement.calculateChargeDays());
        // Validate final charge
        assertEquals(2.68, agreement.calculateFinalCharge(), 0.01);
    }

    @Test
    void test3() {
        LocalDate checkoutDate = LocalDate.of(2025, 7, 2);
        int rentalDays = 5;
        int discountPercent = 25;
        Tool tool = rentalService.getTool("JAKD");
        ToolType toolType = rentalService.getToolType(tool.getType());
        ToolRentalAgreement agreement = new ToolRentalAgreement(tool, toolType, rentalDays, checkoutDate, discountPercent);
        agreement.printAgreement();
        // Validate due date
        assertEquals(LocalDate.of(2025, 7, 7), agreement.calculateDueDate());

        // Validate charge days
        assertEquals(2, agreement.calculateChargeDays());
        // Validate final charge
        assertEquals(4.48, agreement.calculateFinalCharge(), 0.01);
    }

    @Test
    void test4() {
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 6;
        int discountPercent = 0;

        Tool tool = rentalService.getTool("JAKR");
        ToolType toolType = rentalService.getToolType(tool.getType());
        ToolRentalAgreement agreement = new ToolRentalAgreement(tool, toolType, rentalDays, checkoutDate, discountPercent);
        agreement.printAgreement();

        // Validate due date
        assertEquals(LocalDate.of(2015, 9, 9), agreement.calculateDueDate());
        // Validate charge days
        assertEquals(3, agreement.calculateChargeDays());
        // Validate final charge
        assertEquals(8.97, agreement.calculateFinalCharge(), 0.01);
    }

    @Test
    void test5() {
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 9;
        int discountPercent = 0;

        Tool tool = rentalService.getTool("JAKR");
        ToolType toolType = rentalService.getToolType(tool.getType());
        ToolRentalAgreement agreement = new ToolRentalAgreement(tool, toolType, rentalDays, checkoutDate, discountPercent);
        agreement.printAgreement();
        // Validate due date
        assertEquals(LocalDate.of(2015, 7, 11), agreement.calculateDueDate());
        // Validate charge days
        assertEquals(5, agreement.calculateChargeDays());
        // Validate final charge
        assertEquals(14.95, agreement.calculateFinalCharge(), 0.01);
    }

    @Test
    void test6() {
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 4;
        int discountPercent = 50;

        Tool tool = rentalService.getTool("JAKR");
        ToolType toolType = rentalService.getToolType(tool.getType());
        ToolRentalAgreement agreement = new ToolRentalAgreement(tool, toolType, rentalDays, checkoutDate, discountPercent);
        agreement.printAgreement();

        // Validate due date
        assertEquals(LocalDate.of(2020, 7, 6), agreement.calculateDueDate());
        // Validate charge days
        assertEquals(1, agreement.calculateChargeDays());
        // Validate final charge
        assertEquals(1.50, agreement.calculateFinalCharge(), 0.011);
    }


    private static ToolType createToolType(String name, BigDecimal dailyCharge, boolean weekdayChargeEnabled,
                                           boolean weekendChargeEnabled,
                                           boolean holidayChargeEnabled) {
        ToolType toolType = new ToolType();
        toolType.setName(name);
        toolType.setDailyCharge(dailyCharge);
        toolType.setWeekdayChargeEnabled(weekdayChargeEnabled);
        toolType.setWeekendChargeEnabled(weekendChargeEnabled);
        toolType.setHolidayChargeEnabled(holidayChargeEnabled);
        return toolType;
    }

    private static Tool createTool(String code, String toolType, String brand) {
        Tool tool = new Tool();
        tool.setCode(code);
        tool.setType(toolType);
        tool.setBrand(brand);
        return tool;
    }
}
