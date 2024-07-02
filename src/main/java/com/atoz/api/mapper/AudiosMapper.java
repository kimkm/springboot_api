package com.atoz.api.mapper;

import com.atoz.api.model.Audios;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AudiosMapper {
    List<Audios> findAll() throws Exception;

    Audios findById(int id) throws Exception;

    void insert(Audios audios) throws Exception;

    void delete(int id) throws Exception;
}
