package com.expensetracker.backend.entity;

/**
 * Fixed set of expense categories. Kept as an enum (rather than a free-text
 * field) so category-based summaries are reliable and typo-proof.
 */
public enum Category {
    FOOD,
    RENT,
    TRAVEL,
    UTILITIES,
    ENTERTAINMENT,
    HEALTH,
    SHOPPING,
    EDUCATION,
    OTHER
}
