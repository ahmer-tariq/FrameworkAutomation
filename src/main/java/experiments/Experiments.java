package experiments;

import java.util.Locale;

import com.github.javafaker.Faker;

public class Experiments {

	public static void main (String [] args) {
		
		Faker faker = new Faker(new Locale("en-PAK"));
		for (int i=0; i<10; i++) { 
		
		String Fname = faker.name().firstName();
		String Lname = faker.name().lastName();
		String Address = faker.address().fullAddress();
		String Phone =  faker.phoneNumber().cellPhone();
		
		System.out.println("First Name: " + Fname);
		System.out.println("Last Name: " + Lname);
		System.out.println("Address: " + Address);
		System.out.println("Phone: " + Phone);
		System.out.println();
		}
	
	}
}
