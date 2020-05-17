package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"guru.*"})
@EntityScan(basePackages = {"guru.*"})
//@EnableJpaRepositories(basePackages = {"guru.*"})
public class Spring5RecipeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5RecipeAppApplication.class, args);
	}
}
