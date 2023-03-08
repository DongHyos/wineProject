package com.example.wine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
@DynamicInsert
@DynamicUpdate
public class Member {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "address")
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "phone")
    private String phone;

    @Column(name = "agreement")
    private String agreement;

    @Column(name = "role")
    @ColumnDefault("'ROLE_USER'")
    private String role;
}
