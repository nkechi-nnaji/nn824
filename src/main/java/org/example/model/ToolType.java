package org.example.model;

import java.math.BigDecimal;

public class ToolType {
    private String name;
    private BigDecimal dailyCharge;
    private boolean isWeekdayChargeEnabled;
    private boolean isWeekendChargeEnabled;
    private boolean isHolidayChargeEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isWeekdayChargeEnabled() {
        return isWeekdayChargeEnabled;
    }

    public void setWeekdayChargeEnabled(boolean weekdayChargeEnabled) {
        isWeekdayChargeEnabled = weekdayChargeEnabled;
    }

    public boolean isWeekendChargeEnabled() {
        return isWeekendChargeEnabled;
    }

    public void setWeekendChargeEnabled(boolean weekendChargeEnabled) {
        isWeekendChargeEnabled = weekendChargeEnabled;
    }

    public boolean isHolidayChargeEnabled() {
        return isHolidayChargeEnabled;
    }

    public void setHolidayChargeEnabled(boolean holidayChargeEnabled) {
        isHolidayChargeEnabled = holidayChargeEnabled;
    }
}
