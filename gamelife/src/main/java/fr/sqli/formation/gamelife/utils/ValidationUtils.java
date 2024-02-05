package fr.sqli.formation.gamelife.utils;

import fr.sqli.formation.gamelife.ex.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class ValidationUtils {

    private ValidationUtils() {
        throw new IllegalAccessError();
    }

    public static boolean isNotNull(Object pObject, String pMessage) throws ParameterException {
        if (pObject != null) {
            return true;
        }

        throw new ParameterException(pMessage);
    }

    public static boolean isNull(Object pObject, String pMessage) throws ParameterException {
        if (pObject == null) {
            return true;
        }

        throw new ParameterException(pMessage);
    }

    public static boolean isPositiveInt(Number pNumber, String pMessage) throws ParameterException {
        if (ValidationUtils.isNotNull(pNumber, pMessage) && pNumber.intValue() > 0) {
            return true;
        }

        throw new ParameterException(pMessage);
    }

    //TODO: rename
    public static void validateFieldEnum(Object[] pEnums, String pNomFieldEnum, String pMessage) throws ParameterException {
        if (Character.isDigit(pNomFieldEnum.charAt(0))) {
            pNomFieldEnum = "_" + pNomFieldEnum;
        }

        String finalPNomFieldEnum = pNomFieldEnum;

        boolean valeurFieldExiste = Arrays.stream(pEnums)
                .map(Object::toString)
                .anyMatch(value -> value.equalsIgnoreCase(finalPNomFieldEnum.replaceAll("\\s", "")));

        if (!valeurFieldExiste) {
            throw new ParameterException(pMessage);
        }
    }

    public static void validateFields(Object pObjectDtoIn) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParameterException {
        Field[] fields = pObjectDtoIn.getClass().getDeclaredFields();

        for(Field field: fields) {
            if (field.getName().equals("id") || field.getName().equals("serialVersionUID") || field.getName().contains("Dao")) {
                continue;
            }

            String fieldName = field.getName();

            Method getter = pObjectDtoIn.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
            Object valeurField = getter.invoke(pObjectDtoIn);

            if (valeurField == null) {
                throw new ParameterException(fieldName + " est null");
            }

            if (valeurField instanceof String) {
                if (valeurField.equals("")) {
                    throw new ParameterException(fieldName + " est une chaîne de caractères vide");
                }
            }
        }
    }

    public static void updateFieldsIfDifferentBetweenEntityAndDto(Object pObjectEntity, Object pObjectDtoIn) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = pObjectDtoIn.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id") || field.getName().equals("serialVersionUID") || field.getName().contains("Dao")) {
                continue;
            }

            String fieldName = field.getName();

            Method getter = pObjectDtoIn.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
            Object valeurField = getter.invoke(pObjectDtoIn);

            Method getterEntity = pObjectEntity.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
            Object valeurFieldEntity = getterEntity.invoke(pObjectEntity);

            Method setterEntity = pObjectEntity.getClass().getMethod("set" + StringUtils.capitalize(fieldName), field.getType());

            if (valeurField instanceof String && valeurFieldEntity instanceof String) {
                if (!valeurFieldEntity.equals(valeurField)) {
                    setterEntity.invoke(pObjectEntity, valeurField);
                }
            }

            if (valeurField instanceof Integer && valeurFieldEntity instanceof Integer) {
                if (!valeurFieldEntity.equals(valeurField)) {
                    setterEntity.invoke(pObjectEntity, valeurField);
                }
            }

            if (valeurField instanceof Double && valeurFieldEntity instanceof Double) {
                if (!valeurFieldEntity.equals(valeurField)) {
                    setterEntity.invoke(pObjectEntity, valeurField);
                }
            }

            if (valeurField instanceof Boolean && valeurFieldEntity instanceof Boolean) {
                if (!valeurFieldEntity.equals(valeurField)) {
                    setterEntity.invoke(pObjectEntity, valeurField);
                }
            }

            setterEntity.invoke(pObjectEntity, valeurField);
        }
    }
}
