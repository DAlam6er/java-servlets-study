package com.dmdev.http.mapper;

import com.dmdev.http.dto.ReadUserDto;
import com.dmdev.http.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReadUserMapper implements Mapper<User, ReadUserDto>
{
    private static final ReadUserMapper INSTANCE = new ReadUserMapper();

    @Override
    public ReadUserDto mapFrom(User object)
    {
        // для подобного boilerplate кода существует специальная библиотека
        // MapStruct https://mapstruct.org
        return ReadUserDto.builder()
            .id(object.getId())
            .name(object.getName())
            .birthday(object.getBirthday())
            .email(object.getEmail())
            .image(object.getImage())
            .role(object.getRole())
            .gender(object.getGender())
            .build();
    }

    public static ReadUserMapper getInstance()
    {
        return INSTANCE;
    }
}
