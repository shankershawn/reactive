/**
 * 
 */
package shankarsan.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

/**
 * @author SHANKARSAN
 *
 */

//@EnableCaching
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application {
	public static void main(String [] args) {
		SpringApplication.run(Application.class);
	}
}
