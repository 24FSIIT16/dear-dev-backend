package ch.fhnw.deardevbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Builder
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId")
    private Integer userId;

    private String type;
    private String provider;

    @Column(name = "providerAccountId")
    private String providerAccountId;

    @Column(name = "refresh_token")
    private String refresh_token;
    private String access_token;
    private Long expires_at;
    private String id_token;
    private String scope;
    private String session_state;
    private String token_type;
}
