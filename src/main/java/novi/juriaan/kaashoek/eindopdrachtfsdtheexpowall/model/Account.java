package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private LocalDate DOB;


    @Column
    @Lob
    private byte[] profileImage;
    @Column
    private String imageName;



    @OneToOne(mappedBy = "account")
    @JoinColumn(name = "account-id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<ArtWork> artWorkList;

    @OneToMany(mappedBy = "accountMes")
    private List<Message> messageList;

    public Account(Long id, String firstName, String lastName, LocalDate DOB, byte[] profileImage, String imageName, User user, List<ArtWork> artWorkList, List<Message> messageList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.profileImage = profileImage;
        this.imageName = imageName;
        this.user = user;
        this.artWorkList = artWorkList;
        this.messageList = messageList;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ArtWork> getArtWorkList() {
        return artWorkList;
    }

    public void setArtWorkList(List<ArtWork> artWorkList) {
        this.artWorkList = artWorkList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
