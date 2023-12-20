package com.GlamourByNora.api.dto;

import org.springframework.stereotype.Component;

@Component
public class UserResponseDto {
    private String firstname;
    private String lastname;
    private String country;
    private String state;
    private String email;
    private Long phone_no;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(Long phone_no) {
        this.phone_no = phone_no;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", phone_no=" + phone_no +
                '}';
    }
}
