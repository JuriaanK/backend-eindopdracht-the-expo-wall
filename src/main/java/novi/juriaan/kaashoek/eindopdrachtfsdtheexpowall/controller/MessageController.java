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

    @GetMapping(value ="/byreceiver/{id}")
    public ResponseEntity<List<MessageDTO>> getByReceiver(@PathVariable ("id") Long id){

        List<MessageDTO> messageDTOs =messageService.getMessageByReciever(id);

        return ResponseEntity.ok().body(messageDTOs);
    }

    @PostMapping(value = "")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO){

        MessageDTO newMessage = messageService.creatMessage(messageDTO);

        return ResponseEntity.created(null).body(newMessage);
    }



}
