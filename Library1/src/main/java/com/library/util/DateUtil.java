package com.library.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Date utility class for date calculations and penalty computation
 */
public class DateUtil {

    // Default issue period in days (14 days)
    private static final int DEFAULT_ISSUE_PERIOD_DAYS = 14;

    // Penalty amount per day (₹5)
    private static final BigDecimal PENALTY_PER_DAY = new BigDecimal("5.00");

    /**
     * Calculate due date from issue date
     * 
     * @param issueDate       Issue date
     * @param issuePeriodDays Number of days for the issue period
     * @return Due date
     */
    public static Date calculateDueDate(Date issueDate, int issuePeriodDays) {
        LocalDate issue = issueDate.toLocalDate();
        LocalDate due = issue.plusDays(issuePeriodDays);
        return Date.valueOf(due);
    }

    /**
     * Calculate due date using default issue period (14 days)
     * 
     * @param issueDate Issue date
     * @return Due date
     */
    public static Date calculateDueDate(Date issueDate) {
        return calculateDueDate(issueDate, DEFAULT_ISSUE_PERIOD_DAYS);
    }

    /**
     * Calculate penalty for late return
     * Penalty = ₹5 per day late
     * 
     * @param dueDate    Due date
     * @param returnDate Return date
     * @return Penalty amount
     */
    public static BigDecimal calculatePenalty(Date dueDate, Date returnDate) {
        if (returnDate == null || dueDate == null) {
            return BigDecimal.ZERO;
        }

        LocalDate due = dueDate.toLocalDate();
        LocalDate returned = returnDate.toLocalDate();

        // If returned on or before due date, no penalty
        if (!returned.isAfter(due)) {
            return BigDecimal.ZERO;
        }

        // Calculate days late
        long daysLate = ChronoUnit.DAYS.between(due, returned);

        // Calculate penalty: daysLate * ₹5
        BigDecimal penalty = PENALTY_PER_DAY.multiply(new BigDecimal(daysLate));

        return penalty;
    }

    /**
     * Calculate penalty using current date as return date
     * 
     * @param dueDate Due date
     * @return Penalty amount
     */
    public static BigDecimal calculatePenaltyToday(Date dueDate) {
        Date today = Date.valueOf(LocalDate.now());
        return calculatePenalty(dueDate, today);
    }

    /**
     * Calculate days between two dates
     * 
     * @param startDate Start date
     * @param endDate   End date
     * @return Number of days between dates
     */
    public static long daysBetween(Date startDate, Date endDate) {
        LocalDate start = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Check if a book is overdue
     * 
     * @param dueDate Due date
     * @return true if overdue, false otherwise
     */
    public static boolean isOverdue(Date dueDate) {
        LocalDate due = dueDate.toLocalDate();
        LocalDate today = LocalDate.now();
        return today.isAfter(due);
    }

    /**
     * Get current date as SQL Date
     * 
     * @return Current date
     */
    public static Date getCurrentDate() {
        return Date.valueOf(LocalDate.now());
    }

    /**
     * Get penalty per day amount
     * 
     * @return Penalty per day
     */
    public static BigDecimal getPenaltyPerDay() {
        return PENALTY_PER_DAY;
    }

    /**
     * Get default issue period in days
     * 
     * @return Default issue period
     */
    public static int getDefaultIssuePeriod() {
        return DEFAULT_ISSUE_PERIOD_DAYS;
    }

    /**
     * Format penalty amount for display
     * 
     * @param penalty Penalty amount
     * @return Formatted string (e.g., "₹25.00")
     */
    public static String formatPenalty(BigDecimal penalty) {
        if (penalty == null) {
            return "₹0.00";
        }
        return "₹" + penalty.setScale(2, java.math.RoundingMode.HALF_UP).toString();
    }
}
