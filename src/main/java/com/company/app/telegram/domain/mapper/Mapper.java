package com.company.app.telegram.domain.mapper;

import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

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
        List<ChatDto> dtoList = chatList.stream()
                .map(Mapper::of)
                .collect(Collectors.toList());

        // пока что закостылим
        dtoList.forEach(chatDto -> chatDto.getHistoryList().forEach(history -> history.setChat(null)));
        dtoList.forEach(chatDto -> chatDto.getSubscriptions().forEach(subscription -> subscription.setChats(null)));
        dtoList.forEach(chatDto -> chatDto.getUserInfo().setChat(null));

        return dtoList;
    }
}
