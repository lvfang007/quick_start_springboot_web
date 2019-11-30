package com.lvfang.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvfang.mybatisplus.entity.Phone;
import com.lvfang.mybatisplus.mapper.PhoneMapper;
import com.lvfang.mybatisplus.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvfang
 * @since 2019-10-21
 */
@Service
public class PhoneServiceImpl extends ServiceImpl<PhoneMapper, Phone> implements PhoneService {

    @Autowired
    private PhoneMapper phoneMapper;

    public Phone findById(Integer id){
        return phoneMapper.selectById(id);
    }

    public IPage<Phone> findList(Page<Phone> pageInfo, QueryWrapper<Phone> queryWrapper){
        return phoneMapper.selectPage(pageInfo,queryWrapper);
    }
}
