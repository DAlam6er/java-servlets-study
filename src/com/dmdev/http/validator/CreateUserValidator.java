package com.dmdev.http.validator;

import com.dmdev.http.dto.CreateUserDto;
import com.dmdev.http.entity.Gender;
import com.dmdev.http.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

// singleton, т.к. могут быть зависимости на другие бины
@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto>
{
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object)
    {
        var validationResult = new ValidationResult();

        var userName = object.getName();
        if (userName == null || userName.trim().isEmpty()) {
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }

        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday","Birthday is invalid"));
        }

        var email = object.getEmail();
        if (email == null || email.trim().isEmpty()) {
            validationResult.add(Error.of("invalid.email", "E-mail is invalid"));
        }

        var image = object.getImage();
        if (image == null || image.getSize() == 0) {
            validationResult.add(Error.of("invalid.image", "Image is invalid"));
        }

        var password = object.getPassword();
        if (password == null || password.trim().isEmpty()) {
            validationResult.add(Error.of("invalid.name", "Password is invalid"));
        }

        if (Gender.find(object.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance()
    {
        return INSTANCE;
    }
}
