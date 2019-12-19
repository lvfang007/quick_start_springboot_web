package com.lvfang.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lvfang.mybatisplus.entity.Phone;
import com.lvfang.mybatisplus.mapper.PhoneMapper;
import com.lvfang.mybatisplus.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PageInfo<Phone> findList(int pageNum, int pageSize){
        pageNum = pageNum <= 0 ? 1 : pageNum; // 如果未设置查询页数 则默认返回第1页
        pageSize = pageSize <= 0 ? 10 : pageSize; // 如果未设置每天显示条数 则默认10条
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Phone> queryWrapper = new QueryWrapper<>();
        List<Phone> phoneList = phoneMapper.selectList(queryWrapper);
        return new PageInfo<>(phoneList);
    }
}
