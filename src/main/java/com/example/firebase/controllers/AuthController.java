package com.example.firebase.controllers;

import com.example.firebase.services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody Map<String, Object> user) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail((String) user.get("email"))
                    .setPassword((String) user.get("password"));

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

            Map<String, Object> response = new HashMap<>();
            response.put("uid", userRecord.getUid());
            response.put("email", userRecord.getEmail());

            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            // Handle Firebase authentication exception
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "FirebaseAuthException");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception e) {
            // Handle other exceptions
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Exception");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> user) throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login handled on client side.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/protected")
    public ResponseEntity<Map<String, Object>> protectedResource(@AuthenticationPrincipal FirebaseToken decodedToken) {
        Map<String, Object> response = new HashMap<>();
        response.put("uid", decodedToken.getUid());
        response.put("email", decodedToken.getEmail());
        response.put("message", "This is a protected resource");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getUser")
    public ResponseEntity<Map<String, Object>> getUser(@RequestBody Map<String, Object> GetUser) throws Exception {
        String email = (String) GetUser.get("email");
        Map<String, Object> response = new HashMap<>();
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            String uid = userRecord.getUid();

            response.put("used id: ", uid);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to return token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
