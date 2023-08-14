package club.ovelya.socketsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocketSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketSystemApplication.class, args);
    }

}
