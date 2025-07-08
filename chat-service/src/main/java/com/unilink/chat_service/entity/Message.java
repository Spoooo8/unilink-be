package com.unilink.chat_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    private Long senderId;
    private String senderName;// Use sender ID instead of sender name
    private String content;
    private LocalDateTime timeStamp;

}
