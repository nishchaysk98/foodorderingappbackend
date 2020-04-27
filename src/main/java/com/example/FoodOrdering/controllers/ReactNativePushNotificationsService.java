package com.example.FoodOrdering.controllers;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
 
@Service
public class ReactNativePushNotificationsService {
 
  private static final String FIREBASE_SERVER_KEY = "AAAAYVHv5O4:APA91bFZORfg5PwtWeM-hkmtMVFF6g_yLp7ec6x2FxMwQCnrZRP4ICvMEpxW78eNeEOyS-hakFZmDIzgAFsqR5O8UPphXFcbsHv4-y1WDDuduQX-HUQA2qZGaLwR0u1f9C2wcZpw7w_r";
  private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
  
  @Async
  public CompletableFuture<String> send(HttpEntity<String> entity) {
 
    RestTemplate restTemplate = new RestTemplate();
 
    /**
    https://fcm.googleapis.com/fcm/send
    Content-Type:application/json
    Authorization:key=FIREBASE_SERVER_KEY*/
 
    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
    restTemplate.setInterceptors(interceptors);
 
    String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
    
 
    return CompletableFuture.completedFuture(firebaseResponse);
  }
}