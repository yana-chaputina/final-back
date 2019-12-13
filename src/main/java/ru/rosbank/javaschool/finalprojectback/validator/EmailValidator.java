package ru.rosbank.javaschool.finalprojectback.validator;

import ru.rosbank.javaschool.finalprojectback.constraint.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {


    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z].[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+@[a-z]+\\.[a-z]{2,5}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
