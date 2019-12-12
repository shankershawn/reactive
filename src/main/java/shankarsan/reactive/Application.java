/**
 * 
 */
package shankarsan.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author SHANKARSAN
 *
 */

@EnableCaching
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class Application {
	public static void main(String [] args) {
		SpringApplication.run(Application.class);
	}
}
