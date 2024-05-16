package com.example.firebase.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String email;
}
