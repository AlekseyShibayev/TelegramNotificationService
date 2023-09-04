package com.company.app.telegram.domain.mapper;

import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.List;

@UtilityClass
public class Mapper {

    public static ChatDto of(Chat chat) {
        ChatDto chatDto = new ChatDto();
        BeanUtils.copyProperties(chat, chatDto);
        return chatDto;
    }

    public static Chat of(ChatDto chatDto) {
        Chat chat = new Chat();
        BeanUtils.copyProperties(chatDto, chat);
        return chat;
    }

    public static Chat of(Long id, ChatDto chatDto) {
        Chat chat = new Chat();
        BeanUtils.copyProperties(chatDto, chat);
        chat.setId(id);
        return chat;
    }

    public static List<ChatDto> of(List<Chat> chatList) {
        return chatList.stream()
                .map(Mapper::of)
                .toList();
    }

}
