package com.lvfang.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvfang.mybatisplus.entity.Phone;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lvfang
 * @since 2019-10-21
 */
public interface PhoneService extends IService<Phone> {

    Phone findById(Integer id);

    IPage<Phone> findList(Page<Phone> pageInfo,QueryWrapper<Phone> queryWrapper);
}
