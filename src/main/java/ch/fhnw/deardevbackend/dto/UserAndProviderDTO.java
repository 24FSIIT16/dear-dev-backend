package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class UserAndProviderDTO {
    private int id;
    private String name;
    private String email;
    private String provider;
}
