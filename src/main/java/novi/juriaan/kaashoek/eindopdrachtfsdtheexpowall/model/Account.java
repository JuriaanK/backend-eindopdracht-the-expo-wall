package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nickName;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private LocalDate DOB;

    @Lob
    private byte[] profileImage;

    public Account(Long id, String nickName, String firstName, String lastName, LocalDate DOB, byte[] profileImage) {
        this.id = id;
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.profileImage = profileImage;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
