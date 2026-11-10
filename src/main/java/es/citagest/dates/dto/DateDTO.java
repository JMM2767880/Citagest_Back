package es.citagest.dates.dto;

import es.citagest.catalog.state.dto.StateDTO;
import es.citagest.user.dto.UserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class DateDTO {
    private Long id;

    @Size(max = 50)
    private String nameClient;

    @Size(max = 50)
    @Email
    private String emailClient;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private StateDTO state;

    @NotNull
    private UserDTO user;

    @Size(max = 10)
    String dateCode;

    public DateDTO() {}

    public DateDTO(Long id, String nameClient, String emailClient, LocalDateTime dateTime, StateDTO state, UserDTO user, String dateCode) {
        this.id = id;
        this.nameClient = nameClient;
        this.emailClient = emailClient;
        this.dateTime = dateTime;
        this.state = state;
        this.user = user;
        this.dateCode = dateCode;
    }

    public DateDTO(String nameClient, String emailClient, LocalDateTime dateTime, StateDTO state, UserDTO user, String dateCode) {
        this.nameClient = nameClient;
        this.emailClient = emailClient;
        this.dateTime = dateTime;
        this.state = state;
        this.user = user;
        this.dateCode = dateCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    @Override
    public String toString() {
        return "DateDTO{" +
                "id=" + id +
                ", nameClient='" + nameClient + '\'' +
                ", emailClient='" + emailClient + '\'' +
                ", dateTime=" + dateTime +
                ", state=" + state +
                ", user=" + user +
                ", dateCode='" + dateCode + '\'' +
                '}';
    }
}
