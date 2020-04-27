package com.example.FoodOrdering;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.example.FoodOrdering.controllers" })
@SpringBootApplication
public class FoodOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingApplication.class, args);
		FileInputStream fis;
		
		try {
			fis = new FileInputStream("./serviceKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(fis)).build();
			FirebaseApp.initializeApp(options);
			FirebaseAuth auth=FirebaseAuth.getInstance();
			
			// CreateRequest request = new CreateRequest()
			// .setUid("1234")
			// .setEmail("user@example.com")
			// .setPassword("1234sjnjbj");
			// UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
			// System.out.println("Successfully created new user: " + userRecord.getUid());
			// UserRecord record = auth.getUser("1234");
			// String token = auth.createCustomTokenAsync("1234").get();
			
			// System.out.println(token);
			// System.out.println("Successfully fetched user data: " + record.getTokensValidAfterTimestamp());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
/*
eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9nb29nbGUuaWRlbnRpdHkuaWRlbnRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsImV4cCI6MTU4NzEzMzc5OSwiaWF0IjoxNTg3MTMwMTk5LCJpc3MiOiJmaXJlYmFzZS1hZG1pbnNkay1yNHBnd0BhYmNkLTFmNWU3LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwic3ViIjoiZmlyZWJhc2UtYWRtaW5zZGstcjRwZ3dAYWJjZC0xZjVlNy5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsInVpZCI6InNvbWUtaWQifQ.ewilUoSFnnaIHpoXBANzpuQ97f74WTIeD0Vqdt-UGPU2aiYAQARyJBoyyFv8aw_Hdf7Xrp2Gee6i2DrMsRjpA15IGRQuBwpKDeo2dhnyOubxw7OwOziqMHKI5TI8QXit4AoofZvhNCGU9TWFWW4Ntgdq8H3MfNzl0H0SQQ8rdjLQeOsRpAYOF54ML_gAKH45MyLnQuJzfzr3vV4Mu8Urk0UtHetJiPRck_slsKnQoIkrgjNpYFDVfPVvoEn7lGiCw-jX_O_iPzLbv-Ymmdx3r2ZZtymwy1LVxJKSdwbKCEd04qCC96gAx5gPtfpuKBVabUNkcUdb3mp6BQoI-Og38g
*/