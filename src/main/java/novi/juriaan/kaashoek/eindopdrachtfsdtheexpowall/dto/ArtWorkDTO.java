package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;

public class ArtWorkDTO {

    public Long artWorkID;
    public Long likes;
    public String fileName;

    public AccountDTO account;

    @Lob
    public byte[] artWorkImage;

    public static ArtWorkDTO fromArtWork(ArtWork artWork){
        ArtWorkDTO dto = new ArtWorkDTO();

        dto.artWorkID = artWork.getArtWorkID();
        dto.likes = artWork.getLikes();
        dto.artWorkImage = artWork.getArtWorkImage();
        dto.fileName = artWork.getFilename();
        dto.account = AccountDTO.fromAccount(artWork.getAccount());

        return dto;
    }

    public static ArtWork toArtWork(ArtWorkDTO artWorkDTO){
        ArtWork artWork = new ArtWork();

        artWork.setArtWorkID(artWorkDTO.artWorkID);
        artWork.setLikes(artWorkDTO.likes);
        artWork.setArtWorkImage(artWorkDTO.artWorkImage);
        artWork.setFilename(artWorkDTO.fileName);
        artWork.setAccount(AccountDTO.toAccount(artWorkDTO.account));

        return artWork;
    }
}
