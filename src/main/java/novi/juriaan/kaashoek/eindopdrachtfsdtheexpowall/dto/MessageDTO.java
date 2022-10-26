package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;


public class MessageDTO {

    public long messageID;
    private String sender;
    private String receiver;
    public String messageContent;

    public static MessageDTO fromMessage(Message message){
        MessageDTO dto = new MessageDTO();

        dto.messageID = message.getMessageID();
        dto.sender = message.getSender();
        dto.receiver = message.getReceiver();
        dto.messageContent = message.getMessage();

        return dto;

    }

    public static Message toMessage(MessageDTO messageDTO){
        Message message = new Message();

        message.setMessageID(messageDTO.messageID);
        message.setSender(messageDTO.sender);
        message.setReceiver(messageDTO.receiver);
        message.setMessage(messageDTO.messageContent);

        return message;
    }
}
