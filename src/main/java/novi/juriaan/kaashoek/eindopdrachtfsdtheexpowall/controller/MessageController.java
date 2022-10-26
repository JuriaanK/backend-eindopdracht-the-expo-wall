package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.MessageDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.MessageService;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    private final MessageService messageService;

    @GetMapping(value = "")
    public ResponseEntity<List<MessageDTO>> getAllMessages (){

        List<MessageDTO> messageDTOs = messageService.getMessages();

        return ResponseEntity.ok().body(messageDTOs);
    }

    @GetMapping(value ="/conversation/{sender}/{receiver}")
    public ResponseEntity<List<MessageDTO>> getConversation(@PathVariable ("sender") String sender , @PathVariable ("reciever") String receiver){

        List<MessageDTO> messageDTOs =messageService.getConversation(sender, receiver);

        return ResponseEntity.ok().body(messageDTOs);
    }

    @PostMapping(value = "")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO){

        MessageDTO newMessage = messageService.creatMessage(messageDTO);

        return ResponseEntity.created(null).body(newMessage);
    }



}