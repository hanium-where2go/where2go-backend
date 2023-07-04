package hanium.where2go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Where2goApplication {

	public static void main(String[] args) {
		SpringApplication.run(Where2goApplication.class, args);
	}

}
