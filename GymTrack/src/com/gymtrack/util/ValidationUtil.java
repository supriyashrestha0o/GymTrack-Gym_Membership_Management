package com.gymtrack.util;

import java.time.LocalDate;
// ValidationUtil.java — Utility class
public class ValidationUtil {

    // Check that a string is not null or blank
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Email must contain '@'
    public static boolean isValidEmail(String email) {
        return isNotEmpty(email) && email.contains("@");
    }

    // Phone must be exactly 10 digits
    public static boolean isValidPhone(String phone) {
        return isNotEmpty(phone) && phone.matches("\\d{10}");
    }

    // Password and confirm password must match
    public static boolean passwordsMatch(String password, String confirmPassword) {
        return isNotEmpty(password) && password.equals(confirmPassword);
    }

    // Trainer's experience must be more than 2 years
    public static boolean isValidExperience(int years) {
        return years > 2;
    }

    // Start date must NOT be in the past
    public static boolean isValidStartDate(String startDateStr) {
        if (!isNotEmpty(startDateStr)) return false;
        LocalDate startDate = LocalDate.parse(startDateStr);
        return !startDate.isBefore(LocalDate.now());
    }

    // Date of Birth must not be in the future
    public static boolean isValidDob(String dobStr) {
        if (!isNotEmpty(dobStr)) return false;
        LocalDate dob = LocalDate.parse(dobStr);
        return !dob.isAfter(LocalDate.now());
    }

    // MEMBER VALIDATION 
    public static String validateMemberForm(
            String fullName,
            String email,
            String phone,
            String address,
            String username,
            String password,
            String confirmPassword,
            String goal,
            String planType,
            String durationStr,
            String startDate,
            String dob,
            String gender
    ) {
        if (!isNotEmpty(fullName))    return "Full Name is required.";
        if (!isNotEmpty(username))    return "Username is required.";
        if (!isNotEmpty(password))    return "Password is required.";
        if (!isNotEmpty(address))     return "Address is required.";
        if (!isValidEmail(email))     return "Email must contain '@'.";
        if (!isValidPhone(phone))     return "Phone must be exactly 10 digits.";
        if (!passwordsMatch(password, confirmPassword)) return "Passwords do not match.";
        if (!isNotEmpty(planType))    return "Please select a Plan Type.";
        if (!isNotEmpty(goal))        return "Please enter your fitness goal.";
        if (!isNotEmpty(dob) || !isValidDob(dob)) return "Please select a valid Date of Birth.";
        if (!isNotEmpty(gender))      return "Please select Gender.";

        int duration = 0;
        try { duration = Integer.parseInt(durationStr); }
        catch (NumberFormatException e) { return "Duration must be a number."; }
        if (duration < 1) return "Duration must be at least 1 month.";

        if (!isNotEmpty(startDate) || !isValidStartDate(startDate))
            return "Start date cannot be in the past.";

        return null; // All good
    }

    // TRAINER VALIDATION 
    public static String validateTrainerForm(
            String fullName, String email, String phone,
            String username, String password, String confirmPassword,
            String speciality, String experienceStr
    ) {
        if (!isNotEmpty(fullName))    return "Full Name is required.";
        if (!isNotEmpty(username))    return "Username is required.";
        if (!isNotEmpty(password))    return "Password is required.";
        if (!isValidEmail(email))     return "Email must contain '@'.";
        if (!isValidPhone(phone))     return "Phone must be exactly 10 digits.";
        if (!passwordsMatch(password, confirmPassword)) return "Passwords do not match.";
        if (!isNotEmpty(speciality))  return "Please select a Speciality.";

        int experience = 0;
        try { experience = Integer.parseInt(experienceStr); }
        catch (NumberFormatException e) { return "Experience must be a number."; }
        if (!isValidExperience(experience)) return "Trainer experience must be more than 2 years.";

        return null;
    }

    // MEMBERSHIP AMOUNT 
    // Normal plan = Rs. 1500/month, Premium = Rs. 3000/month
    public static double calculateMembershipAmount(String planType, int durationMonths) {
        double ratePerMonth = planType.equals("Premium") ? 3000.0 : 1500.0;
        return ratePerMonth * durationMonths;
    }
}