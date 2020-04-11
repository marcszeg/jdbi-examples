package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user = User.builder()
                .name("James Bond")
                .username("007")
                .password("password")
                .email("jamesbond@secretagent.co.uk")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1920-11-11"))
                .build();
            dao.insert(user);

            User bladerunner = User.builder()
                    .name("Joe")
                    .username("KD6-3.7")
                    .password("joi")
                    .email("officerk@lapd.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("2021-10-06"))
                    .build();
            dao.insert(bladerunner);

            User vuki = User.builder()
                    .name("Chewbacca")
                    .username("Chewie")
                    .password("rooaaar")
                    .email("wookie@milleniumfalcon.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1977-05-25"))
                    .build();
            dao.insert(vuki);


            System.out.println(dao.findById(1));
            System.out.println(dao.findByUsername("007"));
            dao.delete(user);
            dao.list().stream().forEach(System.out::println);
        }
    }
}

