package com.lvfang.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvfang.mybatisplus.entity.Phone;
import com.lvfang.mybatisplus.service.impl.PhoneServiceImpl;
import com.lvfang.mybatisplus.utils.CallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lvfang
 * @since 2019-10-21
 */
@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    PhoneServiceImpl phoneService;

    /**
     * 主键id查询
     * @param id
     * @return
     * http://localhost/phone/1
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public CallResult findById(@PathVariable("id") Integer id){
        Phone phone = phoneService.findById(id);
        return CallResult.success(phone);
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     * http://localhost/phone/list
     */
    @ResponseBody
    @RequestMapping(value = "/list" , method = {RequestMethod.GET,RequestMethod.POST})
    public CallResult findList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        Page<Phone> pageInfo = new Page<>(page,pageSize);
        QueryWrapper<Phone> queryWrapper = new QueryWrapper<>();
        IPage<Phone> result = phoneService.findList(pageInfo,queryWrapper);
        return CallResult.success(result);
    }
}
