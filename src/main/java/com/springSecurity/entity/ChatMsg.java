package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg{

    private String from;

    private String to;

    private String content;

    private LocalDateTime date;

    private String formNickName;


}
