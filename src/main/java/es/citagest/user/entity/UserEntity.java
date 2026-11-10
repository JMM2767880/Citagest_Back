package es.citagest.user.entity;

import es.citagest.catalog.department.entity.DepartmentEntity;
import es.citagest.catalog.desk.entity.DeskEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "users"
)
public class UserEntity {
    @Id
    @Column(
            name = "id",
            nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "name",
            nullable = false,
            length = 50
    )
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "lastname",
            nullable = false,
            length = 50
    )
    private String lastname;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "username",
            unique = true,
            nullable = false,
            length = 50
    )
    private String username;

    @NotNull
    @Size(max = 255)
    @Column(
            name = "password_hash",
            nullable = false,
            length = 255
    )
    private String passwordHash;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "department",
            nullable = false
    )
    private DepartmentEntity departmentEntity;

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "desk",
            unique = true
    )
    private DeskEntity deskEntity;

    public UserEntity(){}

    public UserEntity(String name, String lastname, String username, DepartmentEntity departmentEntity, DeskEntity deskEntity) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.departmentEntity = departmentEntity;
        this.deskEntity = deskEntity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public DeskEntity getDeskEntity() {
        return deskEntity;
    }

    public void setDeskEntity(DeskEntity deskEntity) {
        this.deskEntity = deskEntity;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
