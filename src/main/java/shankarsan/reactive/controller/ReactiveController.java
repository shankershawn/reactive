/**
 * 
 */
package shankarsan.reactive.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shankarsan.reactive.service.ReactiveService;

/**
 * @author SHANKARSAN
 *
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/v1/rx/")
public class ReactiveController {
	
	@Autowired private ReactiveService reactiveService;
	
	@GetMapping(value = "print", produces = MediaType.TEXT_PLAIN_VALUE)
	public String print(@PathVariable Map<String, String> requestParamMap) {
		reactiveService.printService();
		return "printed";
	}
	
	@GetMapping(value = "calldb", produces = MediaType.TEXT_PLAIN_VALUE)
	public String calldb(@PathVariable Map<String, String> requestParamMap) {
		return reactiveService.getDBData();
	}

}
