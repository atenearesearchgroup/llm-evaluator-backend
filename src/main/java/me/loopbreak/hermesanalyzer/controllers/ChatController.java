package me.loopbreak.hermesanalyzer.controllers;

import me.loopbreak.hermesanalyzer.entity.ChatEntity;
import me.loopbreak.hermesanalyzer.entity.messages.AIMessageEntity;
import me.loopbreak.hermesanalyzer.entity.messages.MessageEntity;
import me.loopbreak.hermesanalyzer.objects.request.CreateMessageRequest;
import me.loopbreak.hermesanalyzer.objects.request.UpdateChatRequest;
import me.loopbreak.hermesanalyzer.repository.ChatEntityRepository;
import me.loopbreak.hermesanalyzer.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4321"})
@RequestMapping("/chat")
public class ChatController {

    private final ChatEntityRepository chatEntityRepository;
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatEntityRepository chatEntityRepository,
                          ChatService chatService) {
        this.chatEntityRepository = chatEntityRepository;
        this.chatService = chatService;
    }

    @GetMapping("/{chat}")
    public ChatEntity getChat(@PathVariable Long chat) {
        return chatService.getChat(chat);
    }

    @PutMapping("/{chat}")
    public ChatEntity updateChat(@PathVariable Long chat, @RequestBody UpdateChatRequest request) {
        ChatEntity chatEntity = chatService.getChat(chat);

        request.update(chatEntity);

        return chatEntityRepository.save(chatEntity);
    }

    @PostMapping("/{chat}/message")
    public <T extends MessageEntity> T createMessage(@PathVariable Long chat,
                                                     @RequestBody CreateMessageRequest message) {
        ChatEntity chatEntity = chatService.getChat(chat);

        return chatService.createMessage(message, chatEntity);
    }

    @PostMapping("/{chat}/message/generate")
    public AIMessageEntity generateMessage(@PathVariable Long chat) {
        ChatEntity chatEntity = chatService.getChat(chat);

        return chatService.generateMessage(chatEntity);
    }

    @PostMapping("/{chat}/finish")
    public void finishChat(@PathVariable Long chat, @RequestBody(required = false) Boolean finalize) {
        ChatEntity chatEntity = chatService.getChat(chat);

        chatService.setFinalized(chatEntity, Objects.requireNonNullElse(finalize, true));
    }

}
