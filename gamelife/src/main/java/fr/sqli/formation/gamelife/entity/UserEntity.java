package fr.sqli.formation.gamelife.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "gluser", schema = "gamelife")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderEntity> orders = new ArrayList<>();

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "password", nullable = false, length = 80)
    private String password;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false, length = 80)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "siren_number", length = 9)
    private String sirenNumber;

    @Column(name = "account_status", nullable = false)
    private Boolean accountStatus = false;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

    public UserEntity() {
    }

    public UserEntity(String pLastName, String pFirstName, String pPassword, String pEmail, Integer pStreetNumber, String pStreet, String pCity, Integer pZipCode, String pRole, String pSirenNumber, Boolean pAccountStatus, String pResetPasswordToken) {
        lastName = pLastName;
        firstName = pFirstName;
        password = pPassword;
        email = pEmail;
        streetNumber = pStreetNumber;
        street = pStreet;
        city = pCity;
        zipCode = pZipCode;
        role = pRole;
        sirenNumber = pSirenNumber;
        accountStatus = pAccountStatus;
        resetPasswordToken = pResetPasswordToken;
    }

    public UserEntity(UUID pUserId) {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSirenNumber() {
        return sirenNumber;
    }

    public void setSirenNumber(String sirenNumber) {
        this.sirenNumber = sirenNumber;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public static void validate(String lastName, String firstName, String pwd, String email, String city, Integer streetNumber, String street, String sirenNumber, Integer postalCode) throws Exception {
        if (!(lastName != null && !lastName.trim().isEmpty() &&
                firstName != null && !firstName.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") &&
                pwd != null && !pwd.trim().isEmpty() &&
                pwd.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$") &&
                streetNumber != null && streetNumber >= 0 &&
                street != null && !street.trim().isEmpty() &&
                city != null && !city.trim().isEmpty()) &&
                postalCode != null && postalCode > 0) {
            throw new IllegalArgumentException("Invalid fields");
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserEntity{");
        sb.append("id=").append(id);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", streetNumber=").append(streetNumber);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", zipCode=").append(zipCode);
        sb.append(", role='").append(role).append('\'');
        sb.append(", sirenNumber='").append(sirenNumber).append('\'');
        sb.append(", accountStatus=").append(accountStatus);
        sb.append(", resetPasswordToken='").append(resetPasswordToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
