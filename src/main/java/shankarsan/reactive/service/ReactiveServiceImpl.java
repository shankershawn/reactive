/**
 * 
 */
package shankarsan.reactive.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author SHANKARSAN
 *
 */
@Service
public class ReactiveServiceImpl implements ReactiveService {
	
	@Autowired @Qualifier("mongodbCollection") private MongoCollection<Document> mongoConnection;

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
	public List<String> getDBData() {
		//FindIterable<Document> dataIterable1 = mongoClient.getDatabase("shankarsanDb").getCollection("nairita").find();
		List<String> documentList = new ArrayList<>();
		AggregateIterable<Document> dataIterable1 = mongoConnection
				.aggregate(Arrays
						.asList(Aggregates
								.group("$husband.Salary", Accumulators
										.sum("num_salary", 1))));
		Iterator<Document> documentIterator = dataIterable1.iterator();
		while(documentIterator.hasNext()) {
			documentList.add(documentIterator.next().toJson());
		}
		return documentList;
	}
}
