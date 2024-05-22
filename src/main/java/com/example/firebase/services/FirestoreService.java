package com.example.firebase.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private static final String COLLECTION_NAME = "users";

    public String createUser(String uid, Map<String, Object> user) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(uid);
        ApiFuture<com.google.cloud.firestore.WriteResult> result = docRef.set(user);
        return result.get().getUpdateTime().toString();
    }

    public Map<String, Object> getUser(String uid) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.getData();
        } else {
            return null;
        }
    }

//    public List<Map<String, Object>> getAllUsers() throws InterruptedException, ExecutionException {
//        Firestore db = FirestoreClient.getFirestore();
//        CollectionReference users = db.collection(COLLECTION_NAME);
//        ApiFuture<QuerySnapshot> querySnapshot = users.get();
//        return querySnapshot.get().toObjects(Map.class);
//    }

    public String updateUser(String uid, Map<String, Object> user) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(uid);
        ApiFuture<com.google.cloud.firestore.WriteResult> result = docRef.update(user);
        return result.get().getUpdateTime().toString();
    }

    public String deleteUser(String uid) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(uid);
        ApiFuture<com.google.cloud.firestore.WriteResult> result = docRef.delete();
        return result.get().getUpdateTime().toString();
    }
}

