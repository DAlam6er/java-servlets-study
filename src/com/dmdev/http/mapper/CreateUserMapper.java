package com.dmdev.http.mapper;

import com.dmdev.http.dto.CreateUserDto;
import com.dmdev.http.entity.Gender;
import com.dmdev.http.entity.Role;
import com.dmdev.http.entity.User;
import com.dmdev.http.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

// singleton, т.к. могут быть зависимости на другие бины
@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User>
{
    private static final String IMAGE_FOLDER = "users/";
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto object)
    {
        return User.builder()
            .name(object.getName())
            .birthday(LocalDateFormatter.format(object.getBirthday()))
            .email(object.getEmail())
            .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName())
            .password(object.getPassword())
            .role(Role.valueOf(object.getRole()))
            .gender(Gender.valueOf(object.getGender()))
            .build();
    }

    public static CreateUserMapper getInstance()
    {
        return INSTANCE;
    }
}
