package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.MessageDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Message;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.MessageRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.RoleRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.security.JwtService;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MessageController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private MessageService messageService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;
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

        createMessage = new Message(3L, 0L, 1L, "One of your artworks has been removed, due to inappropriate content.");

        messageDTO1 = MessageDTO.fromMessage(message1);
        messageDTO2 = MessageDTO.fromMessage(message2);
        messageDTO3 = MessageDTO.fromMessage(message3);

        createMessageDTO = MessageDTO.fromMessage(createMessage);
    }

    @Test
    @WithMockUser(username="testuser", roles="USER")
    void shouldGetAllMessages() throws Exception {
        when(messageService.getMessages()).thenReturn(List.of(messageDTO1, messageDTO2, messageDTO3));

        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].messageID").value(1L))
                .andExpect(jsonPath("$[0].sender").value(0L))
                .andExpect(jsonPath("$[0].receiver").value(1L))
                .andExpect(jsonPath("$[0].messageContent").value("One of your artworks has been removed, due to inappropriate content."))
                .andExpect(jsonPath("$[1].messageID").value(2L))
                .andExpect(jsonPath("$[1].sender").value(0L))
                .andExpect(jsonPath("$[1].receiver").value(2L))
                .andExpect(jsonPath("$[1].messageContent").value("One of your artworks has been removed, due to inappropriate content."))
                .andExpect(jsonPath("$[2].messageID").value(3L))
                .andExpect(jsonPath("$[2].sender").value(0L))
                .andExpect(jsonPath("$[2].receiver").value(1L))
                .andExpect(jsonPath("$[2].messageContent").value("One of your artworks has been removed, due to inappropriate content."));
    }

    @Test
    void shouldGetByReceiver() throws Exception {
        when(messageService.getMessageByReciever(1L)).thenReturn(List.of(messageDTO1, messageDTO3));

        mockMvc.perform(MockMvcRequestBuilders.get("/messages/byreceiver/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].messageID").value(1L))
                .andExpect(jsonPath("$[0].sender").value(0L))
                .andExpect(jsonPath("$[0].receiver").value(1L))
                .andExpect(jsonPath("$[0].messageContent").value("One of your artworks has been removed, due to inappropriate content."))
                .andExpect(jsonPath("$[1].messageID").value(3L))
                .andExpect(jsonPath("$[1].sender").value(0L))
                .andExpect(jsonPath("$[1].receiver").value(1L))
                .andExpect(jsonPath("$[1].messageContent").value("One of your artworks has been removed, due to inappropriate content."));
    }

    @Test
    void shouldCreateMessage() throws Exception {
        when(messageService.createMessage(createMessageDTO)).thenReturn(createMessageDTO);

        mockMvc.perform(post("/messages").with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createMessageDTO)))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}