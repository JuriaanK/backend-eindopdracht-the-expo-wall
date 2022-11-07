package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDTO {

    public Long id;
    public String nickName;
    public String firstName;
    public String lastName;
    public LocalDate DOB;

    public UserDTO user;
    @JsonIgnoreProperties("account")
    public static List<ArtWorkDTO> artWork;
    @JsonIgnoreProperties("account")
    public static List<MessageDTO> message;

    public static AccountDTO fromAccount(Account account){
        AccountDTO dto = new AccountDTO();

        dto.id = account.getId();
        dto.nickName = account.getNickName();
        dto.firstName = account.getFirstName();
        dto.lastName = account.getLastName();
        dto.DOB = account.getDOB();
        if (dto.user == null) {
            dto.user = UserDTO.fromUser(account.getUser());}
        if(dto.artWork != null){
            dto.artWork = AccountDTO.convertEntityToArtworkDTO(account.getArtWorkList());}
        if(dto.message != null){
            dto.message = AccountDTO.convertEntityToMessageDTO(account.getMessageList());}

        return dto;
    }

    public static Account toAccount(AccountDTO accountDTO){
        Account account = new Account();

        account.setId(accountDTO.id);
        account.setNickName(accountDTO.nickName);
        account.setFirstName(accountDTO.firstName);
        account.setLastName(accountDTO.lastName);
        account.setDOB(accountDTO.DOB);
        if (accountDTO.user != null){
            account.setUser(UserDTO.toUser(accountDTO.user));}
        if (account.getArtWorkList() != null){
            account.setArtWorkList(accountDTO.convertArtworkDTOtoEntity(artWork));}
        if (account.getMessageList() != null){
            account.setMessageList(accountDTO.convertMessageDTOtoEntity(message));}

        return account;
    }

    public List<ArtWork> convertArtworkDTOtoEntity(List<ArtWorkDTO> artWorkDTOList){
        List<ArtWork> newArtworkList = null;

        for (ArtWorkDTO dto : artWorkDTOList){
            newArtworkList.add(ArtWorkDTO.toArtWork(dto));
        }

        return newArtworkList;
    }

    public static List<ArtWorkDTO> convertEntityToArtworkDTO(List<ArtWork> ArtWorkList){
        List<ArtWorkDTO> newArtworkDTOList = null;

        for (ArtWork artWork : ArtWorkList){
            newArtworkDTOList.add(ArtWorkDTO.fromArtWork(artWork));
        }

        return newArtworkDTOList;
    }

    public List<Message> convertMessageDTOtoEntity(List<MessageDTO> messageDTOList){
        List<Message> newMessageList = null;

        for (MessageDTO dto : messageDTOList){
            newMessageList.add(MessageDTO.toMessage(dto));
        }

        return newMessageList;
    }

    public static List<MessageDTO> convertEntityToMessageDTO(List<Message> messageList){
        List<MessageDTO> newMessageDTOList = null;

        for (Message message : messageList){
            newMessageDTOList.add(MessageDTO.fromMessage(message));
        }

        return newMessageDTOList;
    }

}
