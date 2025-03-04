package com.YYchainsAw.service.impl;

import com.YYchainsAw.mapper.BrandMapper;
import com.YYchainsAw.pojo.Brand;
import com.YYchainsAw.service.BrandService;
import com.YYchainsAw.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceImpl implements BrandService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public List<Brand> selectAll() {
        SqlSession session = factory.openSession();

        BrandMapper mapper = session.getMapper(BrandMapper.class);

        List<Brand> brands = mapper.selectAll();

        session.close();

        return brands;
    }
}
