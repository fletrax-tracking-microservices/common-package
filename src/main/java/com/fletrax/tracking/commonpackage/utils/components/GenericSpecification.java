package com.fletrax.tracking.commonpackage.utils.components;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GenericSpecification {

    public static <T> Specification<T> searchAcrossAllFields(Object searchTerm, Class<T> entityClass) {

        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || (searchTerm instanceof String && ((String) searchTerm).isEmpty())) {
                return criteriaBuilder.conjunction(); // No filtering
            }

            List<Predicate> predicates = new ArrayList<>();

            // Use reflection to iterate over all declared fields in the entity class
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true); // Ensure access to private fields

                Class<?> fieldType = field.getType();

                if (searchTerm instanceof String && fieldType.equals(String.class)) {
                    // String fields: Use LIKE for partial matching
                    String likeSearch = "%" + ((String) searchTerm).toLowerCase() + "%";
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field.getName())), likeSearch));

                } else if (searchTerm instanceof String && Number.class.isAssignableFrom(fieldType)) {
                    // Numeric fields: Try exact match (convert searchTerm to a number if possible)
                    try {
                        Number searchNumber = parseNumber((String) searchTerm, fieldType);
                        if (searchNumber != null) {
                            predicates.add(criteriaBuilder.equal(root.get(field.getName()), searchNumber));
                        }
                    } catch (NumberFormatException e) {
                        // Ignore if conversion fails
                    }

                } else if (searchTerm instanceof String && fieldType.equals(Date.class)) {
                    // Date fields: Parse searchTerm into a Date and filter (e.g., exact match)
                    try {
                        Date searchDate = parseDate((String) searchTerm);
                        if (searchDate != null) {
                            predicates.add(criteriaBuilder.equal(root.get(field.getName()), searchDate));
                        }
                    } catch (Exception e) {
                        // Ignore invalid date parsing
                    }

                } else if (searchTerm instanceof String
                        && (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class))) {
                    // Boolean fields: Match true/false strings
                    if (((String) searchTerm).equalsIgnoreCase("true")
                            || ((String) searchTerm).equalsIgnoreCase("false")) {
                        predicates.add(criteriaBuilder.equal(root.get(field.getName()),
                                Boolean.parseBoolean((String) searchTerm)));
                    }

                } else if (fieldType.isEnum() && searchTerm instanceof String) {
                    // Enum fields: Match against enum name
                    try {
                        Object enumValue = parseEnum((String) searchTerm, fieldType);
                        if (enumValue != null) {
                            predicates.add(criteriaBuilder.equal(root.get(field.getName()), enumValue));
                        }
                    } catch (IllegalArgumentException e) {
                        // Ignore invalid enum values
                    }
                } else if (searchTerm instanceof List<?>) {
                    // Handle lists (arrays) of values
                    predicates.add(root.get(field.getName()).in((List<?>) searchTerm));
                }
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Utility to parse a search term into a number matching the target field type.
     */
    private static Number parseNumber(String searchTerm, Class<?> fieldType) {
        if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.parseInt(searchTerm);
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.parseLong(searchTerm);
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.parseDouble(searchTerm);
        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
            return Float.parseFloat(searchTerm);
        }
        return null; // Unsupported number type
    }

    /**
     * Utility to parse a search term into a Date object.
     * Customize the date parsing logic as per your application's requirements.
     */
    private static Date parseDate(String searchTerm) {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(searchTerm);
        } catch (Exception e) {
            return null; // Invalid date format
        }
    }

    /**
     * Utility to parse a search term into an Enum value.
     */
    private static Object parseEnum(String searchTerm, Class<?> enumType) {
        for (Object enumConstant : enumType.getEnumConstants()) {
            if (enumConstant.toString().equalsIgnoreCase(searchTerm)) {
                return enumConstant;
            }
        }
        return null; // No matching enum value found
    }

    // private Specification<Driver> createSearchSpecification(String search) {
    // return (root, query, criteriaBuilder) -> {
    // if (search == null || search.isEmpty()) {
    // return criteriaBuilder.conjunction(); // No filtering
    // }

    // String likeSearch = "%" + search.toLowerCase() + "%";

    // // Use reflection to get all declared fields of the entity class
    // List<Predicate> predicates = new ArrayList<>();
    // for (Field field : Driver.class.getDeclaredFields()) {
    // if (field.getType().equals(String.class)) { // Only apply search to String
    // fields
    // predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field.getName())),
    // likeSearch));
    // }
    // }

    // return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    // };
    // }

}
