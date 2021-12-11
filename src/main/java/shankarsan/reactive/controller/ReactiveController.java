/**
 * 
 */
package shankarsan.reactive.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping(value = "calldb", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> calldb(@PathVariable Map<String, String> requestParamMap) {
		return reactiveService.getDBData();
	}
	
	@GetMapping(value = "download")
	public void download(@RequestParam(name = "url") String url, @RequestParam(name = "nc_cat") String ncCat, @RequestParam(name = "nc_ohc") String ncOhc, @RequestParam(name = "oh") String oh, @RequestParam(name = "oe") String oe, @RequestParam(name = "like_count") String likeCount) {
		reactiveService.download(url, ncCat, ncOhc, oh, oe, likeCount);
	}

}
