/**
 * 
 */
package shankarsan.reactive.service;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author SHANKARSAN
 *
 */
@Service
public class ReactiveServiceImpl implements ReactiveService {
	
	@Autowired @Qualifier("MongoDBClient") private MongoClient mongoClient;

	@Override
	public void printService() {
		// TODO Auto-generated method stub
		try {
			String [] bawa = {"a","b","c","d","e","f"};
			Observable<String> bawaObserve = Observable.fromArray(bawa);
			
			bawaObserve.delay(3, TimeUnit.SECONDS, Schedulers.io()).subscribe(System.out::print);
			
			Observable<Observable<String>> lbawaObserve = bawaObserve
					.map(String::toUpperCase)
					.window(100, TimeUnit.NANOSECONDS, Schedulers.io(), 2);
			
			Disposable disposable = lbawaObserve
					.subscribe((obr) -> {obr.subscribe(System.out::print);System.out.println();},
					Throwable::printStackTrace,
					() -> System.out.println("\nComplete"));
			
			Thread.sleep(3000);
			disposable.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public String getDBData() {
		
		FindIterable<Document> dataIterable = mongoClient.getDatabase("shankarsanDb").getCollection("nairita").find();
		Iterator<Document> documentIterator = dataIterable.iterator();
		while(documentIterator.hasNext()) {
			System.out.println(documentIterator.next().toJson());
		}
		return null;
	}
	
	/*public static void main(String [] args) throws InterruptedException {
		new ReactiveServiceImpl().printService();
	}*/

}
