package com.example.firebase.controllers;

import com.example.firebase.services.FirestoreService;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/users")
public class FirestoreController {

    @Autowired
    private FirestoreService firestoreService;

    @PostMapping
    public ResponseEntity<String> createUser(@AuthenticationPrincipal FirebaseToken decodedToken,
                                             @RequestBody Map<String, Object> user) throws InterruptedException, ExecutionException {
        String uid = decodedToken.getUid();
        String updateTime = firestoreService.createUser(uid, user);
        return ResponseEntity.ok(updateTime);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String uid) throws InterruptedException, ExecutionException {
        Map<String, Object> user = firestoreService.getUser(uid);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

//    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> getAllUsers() throws InterruptedException, ExecutionException {
//        List<Map<String, Object>> users = firestoreService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }

    @PutMapping("/{uid}")
    public ResponseEntity<String> updateUser(@PathVariable String uid,
                                             @RequestBody Map<String, Object> user) throws InterruptedException, ExecutionException {
        String updateTime = firestoreService.updateUser(uid, user);
        return ResponseEntity.ok(updateTime);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable String uid) throws InterruptedException, ExecutionException {
        String updateTime = firestoreService.deleteUser(uid);
        return ResponseEntity.ok(updateTime);
    }
}
