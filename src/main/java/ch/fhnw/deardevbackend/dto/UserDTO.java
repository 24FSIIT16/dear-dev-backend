package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String loginProvider;
    private String username;
    private Boolean hasTeam;
}
