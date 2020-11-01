package com.example.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 * This is a model class for all users who may appear in this application (regular users and admins)
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class ConfirmationToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Integer tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne
    @JoinColumn
    private Authuser authuser;

    public ConfirmationToken(Authuser authuser) {
        this.authuser = authuser;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

}