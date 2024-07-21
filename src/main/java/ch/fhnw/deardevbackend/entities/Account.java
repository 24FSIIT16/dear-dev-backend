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
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    private String type;
    private String provider;

    @Column(name = "providerAccountId")
    private String providerAccountId;


    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "expires_at")
    private Long expiresAt;

    @Column(name = "id_token")
    private String idToken;

    private String scope;

    @Column(name = "session_state")
    private String sessionState;

    @Column(name = "token_type")
    private String tokenType;
}
