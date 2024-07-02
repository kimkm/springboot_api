package com.atoz.api.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Audios {
    private int id;
    private String filename;//파일명
    private MultipartFile awfile;
    private String d;//등록일
    private String i;//장치명
    private int kb=0;//용량
    private int se=0;//재생시간
}
