/**
 * 
 */
package shankarsan.reactive.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
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
	@Autowired private Environment env;

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

	@Override
	public void download(String fileUrl, String ncCat, String ncOhc, String oh, String oe) {
		//String fileUrl = "https://instagram.fhyd2-1.fna.fbcdn.net/v/t51.2885-15/e35/67279283_2776497445737040_5300287398704471267_n.jpg?_nc_ht=instagram.fhyd2-1.fna.fbcdn.net&_nc_cat=1&_nc_ohc=K7IBshnpZZ4AX9deqta&oh=3b08b314b5be3f6dda4be8e2c081f7c0&oe=5E859849";
		Random random = new Random();
		String filePath = env.getProperty("file.path") + random.nextInt() + env.getProperty("file.extn");
		fileUrl = new StringBuilder(fileUrl).append("&").append(ncCat).append("&").append(ncOhc).append("&").append(oh).append("&").append(oe).toString();
		TrustManager trustManager[] = new TrustManager[] {
				new X509TrustManager() {
					
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						// TODO Auto-generated method stub
						
					}
				}
		};
		InputStream in = null;
		FileOutputStream fileOS = null;
		
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustManager, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
			URL url = new URL(fileUrl);
			URLConnection urlConn = url.openConnection();
			
			in = urlConn.getInputStream();
			fileOS = new FileOutputStream(new File(filePath));
			
			byte[] buffer = new byte[2048];
			int length = 0;
			while((length = in.read(buffer)) != -1) {
				fileOS.write(buffer, 0, length);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(null != fileOS) {
				try {
					fileOS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
