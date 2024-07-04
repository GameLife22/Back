package fr.sqli.formation.gamelife.utility.validator;

import fr.sqli.formation.gamelife.utility.constraint.IInValidImagesURLsConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

/**
 * This code snippet represents a part of a Java annotation interface definition.
 * It includes the declaration of the 'payload' method that returns an array of classes extending Payload.
 */
public class ImagesURLsValidator implements ConstraintValidator<IInValidImagesURLsConstraint, List<String>> {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https|file)://" + // Scheme (https or file)
                    "([\\w.-]+)?" + // Host (e.g., domain or IP) - optional for file scheme
                    "(:\\d+)?" + // Port (optional)
                    "(/[\\w/]*)?" + // Path (optional)
                    "(\\?([\\w=&]+)(&[\\w=&]+)*)?" + // Query (optional)
                    "(#\\w*)?" + // Fragment (optional)
                    "(\\.(jpg|jpeg|png))$", // Image file extension (required)
            Pattern.CASE_INSENSITIVE
    );

    /*
    Attention ! la taille maximum d'url dépend du navigateur:
    Microsoft Internet Explorer: 2,083 characters
    Microsoft Edge: 2,083 characters
    Google Chrome: 32,779 characters
    Mozilla Firefox: more than 64,000 characters
    Apple Safari: more than 64,000 characters
    Google Android: 8,192 characters

    private static final int MAX_URL_LENGTH = 2083;
    */

    @Override
    public void initialize(IInValidImagesURLsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> images, ConstraintValidatorContext context) {
        if (images == null || images.isEmpty()) {
            addConstraintViolation(context, "Images must not be empty");
            return false;
        }

        return images.stream().allMatch(imageUrl -> isValidImage(imageUrl, context));
    }

    /**
     * Validates the given image URL based on several criteria:
     * 1. Checks if the URL is not null or empty.
     * 2. Validates the URL format using a regular expression pattern.
     * 3. Verifies that the URL length does not exceed the maximum allowed length.
     *
     * @param imageUrl The image URL to be validated.
     * @param context The context in which the validation is being performed.
     * @return true if the image URL is valid based on all criteria, false otherwise.
     */
    private boolean isValidImage(String imageUrl, ConstraintValidatorContext context) {
        if (imageUrl == null || imageUrl.isBlank()) {
            addConstraintViolation(context, "Image URL is required");
            return false;
        }

        /*
        if (imageUrl.length() > MAX_URL_LENGTH) {
            addConstraintViolation(context, "URL must not exceed " + MAX_URL_LENGTH + " characters");
            return false;
        }
         */

        if (!URL_PATTERN.matcher(imageUrl).matches()) {
            addConstraintViolation(context, "Invalid URL format: " + imageUrl);
            return false;
        }

        return true;
    }

    /**
     * Disables the default constraint violation and adds a custom constraint violation message to the provided context.
     *
     * @param context The context in which the constraint violation is being added.
     * @param message The custom message to be added as a constraint violation.
     */
    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}