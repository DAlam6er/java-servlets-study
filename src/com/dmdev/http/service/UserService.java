package com.dmdev.http.service;

import com.dmdev.http.dao.UserDao;
import com.dmdev.http.dto.CreateUserDto;
import com.dmdev.http.dto.ReadUserDto;
import com.dmdev.http.exception.ValidationException;
import com.dmdev.http.mapper.CreateUserMapper;
import com.dmdev.http.mapper.ReadUserMapper;
import com.dmdev.http.validator.CreateUserValidator;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService
{
    private static final UserService INSTANCE = new UserService();

    // подключаем зависимости:
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ReadUserMapper readUserMapper = ReadUserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public Optional<ReadUserDto> login(String email, String password)
    {
        return userDao.findByEmailAndPassword(email, password)
            .map(readUserMapper::mapFrom);
    }

    @SneakyThrows
    public Integer create(CreateUserDto userDto)
    {
        // 1 этап: validation
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // 2 этап: преобразование (map) dto в entity, т.к. DAO работает с entity
        var userEntity = createUserMapper.mapFrom(userDto);

        // 3 этап: сохраняем преобразованную entity из предыдущего шага
        imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream());
        userDao.save(userEntity);

        // 4 этап: возвращаем требуемый тип (id/entity/loginEntity ...)
        return userEntity.getId();
    }

    public static UserService getInstance()
    {
        return INSTANCE;
    }
}
