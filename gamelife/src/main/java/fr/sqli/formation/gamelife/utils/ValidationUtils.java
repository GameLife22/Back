package fr.sqli.formation.gamelife.utils;

import fr.sqli.formation.gamelife.ex.ParameterException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

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

    public static <T> void validateDto(T dto, String pMethodName) throws IllegalAccessException, ParameterException {
        isNotNull(dto, pMethodName + " - le dto est null");

        Class<?> dtoClass = dto.getClass();
        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }

            field.setAccessible(true);

            Object value = field.get(dto);

            if (value == null) {
                throw new ParameterException(pMethodName + " - l'attribut " + field.getName() + " est null");
            }

            if (value instanceof Collection<?> && ((Collection<?>) value).isEmpty()) {
                throw new ParameterException(pMethodName + " - l'attribut " + field.getName() + " est vide");
            }

            if (value instanceof String && ((String) value).isEmpty()) {
                throw new ParameterException(pMethodName + " - l'attribut " + field.getName() + " est une chaîne de caractères vide");
            }
        }
    }

    public static <T, R> void updateEntityIfDtoDiffers(T pEntity, R pDto) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> dtoClass = pDto.getClass();
        Field[] fields = dtoClass.getDeclaredFields();
        
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }

            String fieldName = field.getName();

            Method getter = pDto.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
            Object valeurField = getter.invoke(pDto);

            Method getterEntity = pEntity.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
            Object valeurFieldEntity = getterEntity.invoke(pEntity);

            Method setterEntity = pEntity.getClass().getMethod("set" + StringUtils.capitalize(fieldName), field.getType());

            if (valeurField instanceof String && valeurFieldEntity instanceof String) {
                if (!valeurFieldEntity.equals(valeurField)) {
                    setterEntity.invoke(pEntity, valeurField);
                }
            }

            if (valeurField instanceof Number && valeurFieldEntity instanceof Number) {
                if (valeurFieldEntity != valeurField) {
                    setterEntity.invoke(pEntity, valeurField);
                }
            }

            if (valeurField instanceof Boolean && valeurFieldEntity instanceof Boolean) {
                if (!valeurFieldEntity.equals(valeurField)) {
                    setterEntity.invoke(pEntity, valeurField);
                }
            }

            setterEntity.invoke(pEntity, valeurField);
        }
    }
}
