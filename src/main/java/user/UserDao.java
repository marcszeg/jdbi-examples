package user;

import ex9.LegoSet;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {
    @SqlUpdate("""
            CREATE TABLE user (
                id IDENTITY PRIMARY KEY,
                username VARCHAR UNIQUE,
                password VARCHAR NOT NULL,
                name VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                gender ENUM('MALE','FEMALE') NOT NULL,
                dob DATE NOT NULL,
                enabled BOOLEAN NOT NULL)""")
    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, name, email, gener, dob, enabled) VALUES (:username, :password, :name, :email, :gener, :dob, :enabled)")
    @GetGeneratedKeys
    long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> getUser(@Bind("id") long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> getUser(@Bind("username") String username);

    @SqlUpdate("DELETE FROM user WHERE username = :username")
    void delete(@BindBean User user);

    @SqlQuery("SELECT *  FROM user")
    List<User> list();
}