package com.atoz.api.controller;
import com.atoz.api.model.Audios;
import com.atoz.api.service.AudiosService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audios")
public class AudiosController {
    @Autowired
    private AudiosService audiosService;

    @GetMapping
    public List<Audios> getAllAudios() throws Exception{
        return audiosService.getAllAudios();
    }

    @GetMapping("/{id}")
    public Audios getAudiosById(@PathVariable int id) throws Exception{
        return audiosService.getAudiosById(id);
    }

    @PostMapping
    public void createAudios(@ModelAttribute("Audios") Audios audios) throws Exception{
        audiosService.createAudios(audios);
    }

    @DeleteMapping("/{id}")
    public void deleteAudios(@PathVariable int id) throws Exception{
        audiosService.deleteAudios(id);
    }

    @GetMapping("/file/{filename}")
    public void download(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        audiosService.fileDownload(filename,request, response);
    }
}
