package com.cxb.mp_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxb.mp_demo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
