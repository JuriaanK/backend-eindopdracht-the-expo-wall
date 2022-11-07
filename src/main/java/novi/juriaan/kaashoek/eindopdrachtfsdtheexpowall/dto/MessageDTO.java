package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;


public class MessageDTO {

    public long messageID;
    public String sender;
    public String receiver;
    public String messageContent;
    @JsonIgnore
    public Account accountMes;

    public static MessageDTO fromMessage(Message message){
        MessageDTO dto = new MessageDTO();

        dto.messageID = message.getMessageID();
        dto.sender = message.getSender();
        dto.receiver = message.getReceiver();
        dto.messageContent = message.getMessage();
        dto.accountMes = message.getAccountMes();

        return dto;

    }

    public static Message toMessage(MessageDTO messageDTO){
        Message message = new Message();

        message.setMessageID(messageDTO.messageID);
        message.setSender(messageDTO.sender);
        message.setReceiver(messageDTO.receiver);
        message.setMessage(messageDTO.messageContent);
        message.setAccountMes(messageDTO.accountMes);

        return message;
    }
}
