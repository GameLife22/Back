package fr.sqli.formation.gamelife.utility.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation for validating a list of image URLs to ensure they are valid URLs.
 * Default error message: "Images must be valid URLs"
 */
@Documented
@Constraint(validatedBy = ImagesURLsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IInValidImagesURLs {

    /**
     * Specifies the default message for the constraint, which is "Images must be valid URLs".
     * Also defines an empty array for groups.
     */
    String message() default "Images must be valid URLs";

    /**
     * Defines the default groups and payload for a constraint.
     */
    Class<?>[] groups() default {};

    /**
     * This code snippet represents a part of a Java annotation interface definition.
     * It includes the declaration of the 'payload' method that returns an array of classes extending Payload.
     */
    Class<? extends Payload>[] payload() default {};
}