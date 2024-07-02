package com.atoz.api.controller;

import com.atoz.api.model.Datas;
import com.atoz.api.service.DatasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datas")
public class DatasController {

    @Autowired
    private DatasService datasService;

    @GetMapping
    public List<Datas> getAllDatas()  throws Exception{
        return datasService.getAllDatas();
    }

    @GetMapping("/{id}")
    public Datas getDatasById(@PathVariable int id)  throws Exception{
        return datasService.getFindById(id);
    }

    @PostMapping
    public void createDatas(@RequestBody Datas datas)  throws Exception{
        datasService.createDatas(datas);
    }

    @DeleteMapping("/{id}")
    public void deleteDatas(@PathVariable int id)  throws Exception{
        datasService.deleteDatas(id);
    }

}
