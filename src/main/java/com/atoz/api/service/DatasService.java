package com.atoz.api.service;

import com.atoz.api.mapper.DatasMapper;
import com.atoz.api.model.Datas;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasService {

    @Resource
    private DatasMapper datasMapper;

    public List<Datas> getAllDatas() throws Exception {
        return datasMapper.findAll();
    }

    public Datas getFindById(int id) throws Exception{
        return datasMapper.findById(id);
    }

    public void createDatas(Datas datas) throws Exception{
        datasMapper.insert(datas);
    }

    public void deleteDatas(int id) throws Exception{
        datasMapper.delete(id);
    }
}