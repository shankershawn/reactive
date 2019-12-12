/**
 * 
 */
package shankarsan.reactive.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author SHANKARSAN
 *
 */
@Service
public class ReactiveServiceImpl implements ReactiveService {

	@Override
	public void printService() throws InterruptedException {
		// TODO Auto-generated method stub
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
				
	}
	
	public static void main(String [] args) throws InterruptedException {
		new ReactiveServiceImpl().printService();
	}

}
