package es.citagest.dates.entity;

import es.citagest.catalog.state.entity.StateEntity;
import es.citagest.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "dates"
)
public class DateEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @Size(max = 50)
    @Column(
            name = "name_client",
            length = 50
    )
    private String nameClient;

    @Size(max = 50)
    @Email
    @Column(
            name = "email_client",
            length = 50
    )
    private String emailClient;

    @NotNull
    @Column(
            name = "date_time",
            nullable = false
    )
    private LocalDateTime dateTime;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "state",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_state_date")
    )
    private StateEntity stateEntity;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_date")
    )
    private UserEntity userEntity;

    @Column(
            name = "date_code",
            length = 10
    )
    String dateCode;


    public DateEntity(){}

    public DateEntity(Long id, String nameClient, String emailClient, LocalDateTime dateTime, StateEntity stateEntity, UserEntity userEntity, String dateCode) {
        this.id = id;
        this.nameClient = nameClient;
        this.emailClient = emailClient;
        this.dateTime = dateTime;
        this.stateEntity = stateEntity;
        this.userEntity = userEntity;
        this.dateCode = dateCode;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public StateEntity getState() {
        return stateEntity;
    }

    public void setState(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "id=" + id +
                ", nameClient='" + nameClient + '\'' +
                ", emailClient='" + emailClient + '\'' +
                ", dateTime=" + dateTime +
                ", stateEntity=" + stateEntity +
                ", userEntity=" + userEntity +
                ", dateCode='" + dateCode + '\'' +
                '}';
    }
}
