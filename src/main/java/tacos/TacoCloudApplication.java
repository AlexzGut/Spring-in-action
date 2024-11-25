package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		// performs the actual bootstrapping of the application, creating
		// the Spring Application context.
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}
