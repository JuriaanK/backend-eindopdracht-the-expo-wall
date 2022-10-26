package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.ArtWorkDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.ArtWorkRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArtWorkService {

    private final ArtWorkRepository artworkRepos;

    public ArtWorkService(ArtWorkRepository artworkRepos) {
        this.artworkRepos = artworkRepos;
    }

    public List<ArtWorkDTO> getArtWorks(){
        List<ArtWorkDTO> collection = new ArrayList<>();
        List<ArtWork> allArtworks = artworkRepos.findAll();

        for (ArtWork artwork : allArtworks){
            collection.add(ArtWorkDTO.fromArtWork(artwork));
        }

        return collection;
    }

    public ArtWorkDTO getArtWork(Long id){
        ArtWorkDTO dto = new ArtWorkDTO();
        Optional<ArtWork> artWork = artworkRepos.findById(id);

        if(artWork.isPresent()){
            dto = ArtWorkDTO.fromArtWork(artWork.get());
        }else{
            throw new UsernameNotFoundException(id.toString());
        }
        return dto;
    }

    public Long createArtWork(ArtWorkDTO artWorkDTO){
        ArtWork artWork = artworkRepos.save(ArtWorkDTO.toArtWork(artWorkDTO));

        return artWork.getArtWorkID();
    }

    public void deleteAccount(Long id){
        artworkRepos.deleteById(id);
    }

    public ArtWork uploadArtWork(MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ArtWork artWork = new ArtWork();
        artWork.setFilename(name);
        artWork.setArtWorkImage(file.getBytes());

        artworkRepos.save(artWork);

        return artWork;

    }

    public ArtWork changeArtWork (Long id, ArtWorkDTO inputDto){
        ArtWork changedArtWork = ArtWorkDTO.toArtWork(inputDto);
        ArtWork artWork = artworkRepos.findById(id).get();

        if(artWork.getLikes() != changedArtWork.getLikes()) {
            artWork.setLikes(changedArtWork.getLikes());
        }
        if(artWork.getArtWorkImage() != changedArtWork.getArtWorkImage()) {
            artWork.setArtWorkImage(changedArtWork.getArtWorkImage());
        }

        return changedArtWork;
    }

}

