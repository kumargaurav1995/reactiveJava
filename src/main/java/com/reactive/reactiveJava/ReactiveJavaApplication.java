package com.reactive.reactiveJava;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ReactiveJavaApplication {

	public static void main(String[] args) {

		Observable<Users> observable = new ReactiveJavaApplication()
				.getData(getUsers());
		observable.subscribe(System.out:: println,
				throwable -> System.out.println("Exception "+ throwable),()-> System.out.println("Completed"));

		//SpringApplication.run(ReactiveJavaApplication.class, args);
	}
	Observable<Users> getData(final List<Users> usersList){
		return Observable.create(subscriber -> {
			if(!subscriber.isUnsubscribed()){
				usersList.stream().forEach(users -> {
					subscriber.onNext(users);
					sleep(1000);
					//subscriber.onError(new RuntimeException("Wow Exception"));
				});
			}
			subscriber.onCompleted();
		});
	}

	private static List<Users> getUsers(){
		List<Users> usersList = new ArrayList<>();
		usersList.add(new Users("Raj",10L));
		usersList.add(new Users("Rajesh",10L));
		usersList.add(new Users("Ramesh",10L));
		return usersList;
	}
	private void sleep(int i){
		try {
			Thread.sleep(i);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	@ToString
	@AllArgsConstructor
	@Data
	static class Users{
		private String name;
		private Long salary;

        public Users(String name, long salary) {

            this.name = name;
            this.salary = salary;
        }
    }
}
