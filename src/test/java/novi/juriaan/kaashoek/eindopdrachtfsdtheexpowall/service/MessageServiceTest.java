package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.MessageDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.UserDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.MessageRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.JwtService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.MyUserDetails;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageServiceTest {


    @Mock
    SecurityConfig securityConfig;

    @Mock
    MyUserDetails myUserDetails;

    @Mock
    MessageRepository messageRepos;

    @InjectMocks
    MessageService messageService;

    @Captor
    ArgumentCaptor<Message> argumentCaptor;

    Message message1;
    Message message2;
    Message message3;

    Message createMessage;

    MessageDTO messageDTO1;
    MessageDTO messageDTO2;
    MessageDTO messageDTO3;

    MessageDTO createMessageDTO;


    @BeforeEach
    void setUp() {
        message1 = new Message(1L, 0L, 1L, "One of your artworks has been removed, due to inappropriate content.");
        message2 = new Message(2L, 0L, 2L, "One of your artworks has been removed, due to inappropriate content.");
        message3 = new Message(3L, 0L, 1L, "One of your artworks has been removed, due to inappropriate content.");

        messageDTO1 = MessageDTO.fromMessage(message1);
        messageDTO2 = MessageDTO.fromMessage(message2);
        messageDTO3 = MessageDTO.fromMessage(message3);


    }

    @Test
    void getMessages() {
        when(messageRepos.findAll()).thenReturn(List.of(message1, message2, message3));

        List<Message> message = messageRepos.findAll();
        List<MessageDTO> messageDTOS = messageService.getMessages();

        assertEquals(message.get(0).getMessageID(), messageDTOS.get(0).messageID);
        assertEquals(message.get(0).getSender(), messageDTOS.get(0).sender);
        assertEquals(message.get(0).getReceiver(), messageDTOS.get(0).receiver);
        assertEquals(message.get(0).getMessage(), messageDTOS.get(0).messageContent);


    }

    @Test
    void getMessageByReciever() {
        when(messageRepos.findByReceiver(2L)).thenReturn(List.of(message2));

        List<Message> message = messageRepos.findByReceiver(2L);
        List<MessageDTO> messageDTO = messageService.getMessageByReciever(2L);

        assertEquals(message.get(0).getMessageID(), messageDTO.get(0).messageID);
        assertEquals(message.get(0).getSender(), messageDTO.get(0).sender);
        assertEquals(message.get(0).getReceiver(), messageDTO.get(0).receiver);
        assertEquals(message.get(0).getMessage(), messageDTO.get(0).messageContent);
    }

}