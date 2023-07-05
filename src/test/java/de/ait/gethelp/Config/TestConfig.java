package de.ait.gethelp.Config;

import de.ait.gethelp.models.User;
import de.ait.gethelp.security.details.AuthenticatedUser;
import net.bytebuddy.build.ToStringPlugin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class TestConfig {
    public static final String MOCK_ADMIN = "admin";
    public static final String MOCK_USER = "jack";

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {


        return new InMemoryUserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (username.equals(MOCK_ADMIN)) {
                    return new AuthenticatedUser(
                            User.builder()
                                    .id(1L)
                                    .email(MOCK_ADMIN)
                                    .role(User.Role.ADMIN)
                                    .build()
                    );
                } else if (username.equals(MOCK_USER)) {
                    return new AuthenticatedUser(
                            User.builder()
                                    .id(1L)
                                    .email(MOCK_USER)
                                    .role(User.Role.USER)
                                    .build()
                    );
                } else throw new UsernameNotFoundException("Пользователь не найден");


            }
        };

    /*
@Bean
@Primary
public UserDetailsService userDetailsService() {

    return new InMemoryUserDetailsManager() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (username.equals(MOCK_USER)) {
                return new AuthenticatedUser(
                        User.builder()
                                .id(1L)
                                .email(MOCK_USER)
                                .role(User.Role.USER)
                                .build()
                );
            } else throw new UsernameNotFoundException("Пользователь не найден");
        }
    };
}

     */


    }
}
