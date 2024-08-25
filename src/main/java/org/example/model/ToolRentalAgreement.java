package org.example.model;

import org.example.utils.HolidayUtil;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class ToolRentalAgreement {
    private Tool tool;
    private ToolType toolType;
    private int rentalDays;
    private LocalDate checkoutDate;
    private int discountPercent;

    public ToolRentalAgreement(Tool tool, ToolType toolType, int rentalDays, LocalDate checkoutDate, int discountPercent) {

        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
        this.tool = tool;
        this.toolType = toolType;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
    }

    public LocalDate calculateDueDate() {
        return checkoutDate.plusDays(rentalDays);
    }

    public int calculateChargeDays() {
        int chargeableDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1); // Start counting from the day after checkout
        while (!currentDate.isAfter(calculateDueDate())) {
            if (isChargeableDay(currentDate, toolType)) {
                chargeableDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return chargeableDays;
    }

    public boolean isChargeableDay(LocalDate date, ToolType toolType) {
        // Assuming tool types may have different "no charge" days logic, this example assumes chargeable on weekdays only
        boolean isWeekend = date.getDayOfWeek().getValue() >= 6; // Saturday = 6, Sunday = 7
        if (isWeekend && toolType.isWeekendChargeEnabled()) {
            return true;
        }
        boolean isHoliday = (date.equals(HolidayUtil.getIndependenceDay(date.getYear())) || date.equals(
                HolidayUtil.getLaborDay(date.getYear())));
        if (isHoliday && toolType.isHolidayChargeEnabled()) {
            return true;
        }

        if (!isHoliday && !isWeekend) {
            return toolType.isWeekdayChargeEnabled();
        }
        return false;
    }

    public double calculatePreDiscountCharge() {
        return Math.round(calculateChargeDays() * toolType.getDailyCharge().floatValue() * 100.0) / 100.0;
    }

    public double calculateDiscountAmount() {
        return Math.round(calculatePreDiscountCharge() * discountPercent / 100.0 * 100.0) / 100.0;
    }

    public double calculateFinalCharge() {
        return Math.round((calculatePreDiscountCharge() - calculateDiscountAmount()) * 100.0) / 100.0;
    }


    public void printAgreement() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMaximumFractionDigits(0);

        System.out.println("Tool code: " + tool.getCode());
        System.out.println("Tool type: " + tool.getType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + checkoutDate.format(dateFormatter));
        System.out.println("Due date: " + calculateDueDate().format(dateFormatter));
        System.out.println("Daily rental charge: " + currencyFormatter.format(toolType.getDailyCharge()));
        System.out.println("Charge days: " + calculateChargeDays());
        System.out.println("Pre-discount charge: " + currencyFormatter.format(calculatePreDiscountCharge()));
        System.out.println("Discount percent: " + percentFormatter.format(discountPercent / 100.0));
        System.out.println("Discount amount: " + currencyFormatter.format(calculateDiscountAmount()));
        System.out.println("Final charge: " + currencyFormatter.format(calculateFinalCharge()));
        System.out.println();
    }
}

