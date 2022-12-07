package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.ArtWorkDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.ArtWorkService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.uploadeResponse.ArtWorkUploadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/artworks")
public class ArtWorkController {

    private final ArtWorkService artWorkService;

    public ArtWorkController(ArtWorkService artWorkService) {
        this.artWorkService = artWorkService;
    }

    @GetMapping(value = "")
    public Collection<ArtWorkDTO> getAllArtWorks(){

        Collection<ArtWorkDTO> artWorkDTOs = artWorkService.getArtWorks();

        return artWorkDTOs;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ArtWorkDTO> getArtWork(@PathVariable("id") Long id){

        ArtWorkDTO optionalArtWork = artWorkService.getArtWork(id);

        return ResponseEntity.ok().body(optionalArtWork);
    }

    @GetMapping(value = "/byowner/{owner}")
    public Collection<ArtWorkDTO> getArtWorkByOwner(@PathVariable("owner") Account owner){

        Collection<ArtWorkDTO> artWorkByOwner = artWorkService.getArtWorkbyOnwer(owner);

        return artWorkByOwner;
    }


    @PostMapping(value = "/upload")
    public ArtWorkUploadResponse createArtWork (@RequestParam("artWorkImage") MultipartFile artWorkImage, @RequestParam("owner") Account owner) throws IOException {
        ArtWork fileDocument = artWorkService.uploadArtWork(artWorkImage, owner);


        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/upload").path(Objects.requireNonNull(artWorkImage.getOriginalFilename())).toUriString();

        String contentType = artWorkImage.getContentType();

        return new ArtWorkUploadResponse(fileDocument.getFilename(), url, contentType );

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ArtWorkDTO> updateArtWork(@PathVariable ("id") Long id , @RequestBody ArtWorkDTO artWorkDTO){

        artWorkService.changeArtWork(id, artWorkDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteArtWork(@PathVariable("id") Long id){

        artWorkService.deleteArtWork(id);

        return ResponseEntity.noContent().build();
    }
}
