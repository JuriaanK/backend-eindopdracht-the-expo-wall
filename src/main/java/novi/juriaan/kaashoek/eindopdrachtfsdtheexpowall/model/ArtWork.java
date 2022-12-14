package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;

import javax.persistence.*;

@Entity
@Table(name = "artworks")
public class ArtWork {

    @Id
    @GeneratedValue
    private Long artWorkID;
    @Column
    private String filename;
    @Column
    private Long likes;

    @Lob
    private byte[] artWorkImage;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Account account;

    public ArtWork(Long artWorkID, String filename, Long likes, byte[] artWorkImage, Account account) {
        this.artWorkID = artWorkID;
        this.filename = filename;
        this.likes = likes;
        this.artWorkImage = artWorkImage;
        this.account = account;
    }

    public ArtWork() {
    }

    public Long getArtWorkID() {
        return artWorkID;
    }

    public void setArtWorkID(Long artWorkID) {
        this.artWorkID = artWorkID;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public byte[] getArtWorkImage() {
        return artWorkImage;
    }

    public void setArtWorkImage(byte[] artWorkImage) {
        this.artWorkImage = artWorkImage;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
