package com.dmdev.http.dao;

import com.dmdev.http.entity.Gender;
import com.dmdev.http.entity.Role;
import com.dmdev.http.entity.User;
import com.dmdev.http.util.ConnectionPool;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Integer, User>
{
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
        INSERT INTO users (name, birthday, email, image, password, role, gender)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
        SELECT id, name, birthday, email, image, password, role, gender
        FROM users
        WHERE email = ? AND password = ?
        """;

    @Override
    @SneakyThrows
    public User save(User entity)
    {
        try (var connection = ConnectionPool.get();
             var preparedStatement =
                 connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS))
        {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getBirthday());
            preparedStatement.setObject(3, entity.getEmail());
            preparedStatement.setObject(4, entity.getImage());
            preparedStatement.setObject(5, entity.getPassword());
            preparedStatement.setObject(6, entity.getRole().name());
            preparedStatement.setObject(7, entity.getGender().name());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
        }
        return entity;
    }

    @Override
    public List<User> findAll()
    {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id)
    {
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password)
    {
        try (var connection = ConnectionPool.get();
             var preparedStatement =
                 connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL))
        {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean delete(Integer id)
    {
        return false;
    }

    @Override
    public void update(User entity)
    {

    }

    private User buildEntity(ResultSet resultSet) throws SQLException
    {
        // для обязательных полей - Role.valueOf(...)
        // для необязательных полей - SomeEnum.find(...).orElse(null)
        // Gender приведен в качестве условного примера, и является обязательным полем
        return User.builder()
            .id(resultSet.getObject("id", Integer.class))
            .name(resultSet.getObject("name", String.class))
            .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
            .email(resultSet.getObject("email", String.class))
            .image(resultSet.getObject("image", String.class))
            .password(resultSet.getObject("password", String.class))
            .role(Role.valueOf(resultSet.getObject("role", String.class)))
            .gender(Gender.find(resultSet.getObject("gender", String.class)).orElse(null))
            .build();
    }

    public static UserDao getInstance()
    {
        return INSTANCE;
    }
}
