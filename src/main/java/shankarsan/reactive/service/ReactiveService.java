/**
 * 
 */
package shankarsan.reactive.service;

import java.util.List;

/**
 * @author SHANKARSAN
 *
 */
public interface ReactiveService {
	public void printService();
	public List<String> getDBData();
	public void download(String url, String ncCat, String ncOhc, String oh, String oe);
}
