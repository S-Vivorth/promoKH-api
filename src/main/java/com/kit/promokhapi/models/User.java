package com.kit.promokhapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Document
@Data
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String email;

    @JsonIgnore
    @NonNull
    private String password;

    @Indexed
    private List<String> savedPromotionIdList;

    private boolean isActive;
    private Date createdDate;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.savedPromotionIdList = new ArrayList<>();
        this.isActive = true;
        this.createdDate = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }
}
