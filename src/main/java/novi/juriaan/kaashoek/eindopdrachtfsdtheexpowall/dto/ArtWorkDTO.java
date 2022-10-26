package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;

public class ArtWorkDTO {

    public Long artWorkID;
    public Long likes;
    public String fileName;

    @Lob
    public byte[] artWorkImage;

    public static ArtWorkDTO fromArtWork(ArtWork artWork){
        ArtWorkDTO dto = new ArtWorkDTO();

        dto.artWorkID = artWork.getArtWorkID();
        dto.likes = artWork.getLikes();
        dto.artWorkImage = artWork.getArtWorkImage();
        dto.fileName = artWork.getFilename();

        return dto;
    }

    public static ArtWork toArtWork(ArtWorkDTO artWorkDTO){
        ArtWork artWork = new ArtWork();

        artWork.setArtWorkID(artWorkDTO.artWorkID);
        artWork.setLikes(artWorkDTO.likes);
        artWork.setArtWorkImage(artWorkDTO.artWorkImage);
        artWork.setFilename(artWorkDTO.fileName);

        return artWork;
    }
}
