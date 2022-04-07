package com.example.MicroServiceWithCircuitBreaker.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String name;

    private String email;

}
