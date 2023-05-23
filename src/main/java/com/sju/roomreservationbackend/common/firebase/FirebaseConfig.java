package com.sju.roomreservationbackend.common.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @Value("classpath:capstone-5641d-firebase-adminsdk.json")
    private Resource resource;

    @PostConstruct
    public void initFirebase() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();

            FirebaseApp.initializeApp(options);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
