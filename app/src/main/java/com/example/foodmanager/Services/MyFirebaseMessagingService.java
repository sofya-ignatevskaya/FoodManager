package com.example.foodmanager.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "The token refreshed:" + token);
       //sendRegistrationToServer(token);
    }
}
