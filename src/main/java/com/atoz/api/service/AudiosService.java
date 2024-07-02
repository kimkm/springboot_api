package com.atoz.api.service;

import com.atoz.api.mapper.AudiosMapper;
import com.atoz.api.model.Audios;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Service
public class AudiosService {
    @Autowired
    private AudiosMapper audiosMapper;

    @Value("${file.upload-dir}")
    private String paths;

    public List<Audios> getAllAudios() throws Exception{
        return audiosMapper.findAll();
    }

    public Audios getAudiosById(int id)  throws Exception{
        return audiosMapper.findById(id);
    }

    public void createAudios(Audios audios)  throws Exception{
        String fileName = null;
        MultipartFile uploadFile = audios.getAwfile();
        if (!uploadFile.isEmpty()) {
            String originalFileName = uploadFile.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFileName);
            UUID uuid = UUID.randomUUID();
            fileName = uuid + "." + ext;
            // 파일저장
            uploadFile.transferTo(new File(paths + fileName));
            audios.setFilename(fileName);
            System.out.println(audios.getFilename());
            // WAV 파일 정보 추출
            if (ext.equalsIgnoreCase("wav")) {
                File wavFile = new File(paths + fileName);
                try {
                    AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(wavFile);
                    long fileSize = wavFile.length() /1024;
                    double duration = (double) fileFormat.getFrameLength() / fileFormat.getFormat().getFrameRate();
                    audios.setKb((int)fileSize);
                    audios.setSe((int)duration);
                    System.out.println("파일 용량: " + fileSize + "KB");
                    System.out.println("재생 시간: " + (int)duration + "초");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // DB저장
            audiosMapper.insert(audios);
        }

    }


    public void deleteAudios(int id)  throws Exception{
        audiosMapper.delete(id);
    }


    public void fileDownload(String filename, HttpServletRequest request, HttpServletResponse response) {

        String realFilename = "";
        System.out.println(filename);

        try {
            String browser = request.getHeader("User-Agent");
            // 파일 인코딩
            if (browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
                filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException 발생");
        }

        realFilename = paths + filename;
        System.out.println(realFilename);
        File file = new File(realFilename);
        if (!file.exists()) {
            return;
        }

        // 파일명 지정
        response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realFilename);
            int cnt = 0;
            byte[] bytes = new byte[512];
            while ((cnt = fis.read(bytes)) != -1) {
                os.write(bytes, 0, cnt);
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
