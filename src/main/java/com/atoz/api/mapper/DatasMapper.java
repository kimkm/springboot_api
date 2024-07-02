package com.atoz.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.atoz.api.model.Datas;

import java.util.List;

@Mapper
public interface DatasMapper {

    List<Datas> findAll() throws Exception;

    Datas findById(int id) throws Exception;

    void insert(Datas datas) throws Exception;

    void delete(int id) throws Exception;
}
