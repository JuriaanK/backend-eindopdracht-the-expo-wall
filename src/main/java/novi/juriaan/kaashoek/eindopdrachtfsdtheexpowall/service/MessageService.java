package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.MessageDTO;
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

    public List<MessageDTO> getConversation(String sender, String receiver){
        List<MessageDTO> collection = new ArrayList<>();
        List<Message> totalList = messageRepos.findAll();
        for(Message message : totalList){
            if (message.getSender().equals(sender) && message.getReceiver().equals(receiver)){
                collection.add(MessageDTO.fromMessage(message));
            }
            if (message.getSender().equals(receiver) && message.getReceiver().equals(sender)){
                collection.add(MessageDTO.fromMessage(message));
            }
        }

        return collection;
    }

    public MessageDTO creatMessage(MessageDTO messageDto){
        Message newMessage = messageRepos.save(MessageDTO.toMessage(messageDto));
        return MessageDTO.fromMessage(newMessage);
    }


}