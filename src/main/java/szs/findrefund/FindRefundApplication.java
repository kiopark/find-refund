package szs.findrefund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FindRefundApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindRefundApplication.class, args);
	}

}
