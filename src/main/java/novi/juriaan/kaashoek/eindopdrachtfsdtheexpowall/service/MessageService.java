package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.ArtWorkDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.MessageDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.ArtWork;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepos;

    public MessageService(MessageRepository messageRepos) {
        this.messageRepos = messageRepos;
    }

    public List<MessageDTO> getMessages() {
        List<MessageDTO> collection = new ArrayList<>();
        List<Message> list = messageRepos.findAll();
        for (Message message : list) {
            collection.add(MessageDTO.fromMessage(message));
        }

        return collection;
    }

    public List<MessageDTO> getMessageByReciever(Long id){
        List<MessageDTO> collection = new ArrayList<>();
        List<Message> allArtworks = messageRepos.findByReceiver(id);
        for (Message message : allArtworks){
            collection.add(MessageDTO.fromMessage(message));
        }

        return collection;
    }

    public MessageDTO creatMessage(MessageDTO messageDto){
        Message newMessage = messageRepos.save(MessageDTO.toMessage(messageDto));
        return MessageDTO.fromMessage(newMessage);
    }


}
