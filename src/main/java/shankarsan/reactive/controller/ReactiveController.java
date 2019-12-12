/**
 * 
 */
package shankarsan.reactive.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SHANKARSAN
 *
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/v1/rx/")
public class ReactiveController {
	
	@GetMapping(value = "print", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String print(@PathVariable Map<String, String> requestParamMap) {
		return "Hello World!";
	}

}
