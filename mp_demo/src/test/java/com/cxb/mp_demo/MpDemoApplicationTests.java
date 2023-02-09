package com.cxb.mp_demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxb.mp_demo.entity.Order;
import com.cxb.mp_demo.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Slf4j
class MpDemoApplicationTests {

    @Resource
    OrderMapper orderMapper;

    @Test
    public void mp_test() {
//        List<Order> orders = orderMapper.selectList(null);
//        log.info("info");
//        for( Order order : orders){
//            log.info(order.toString());
//        }
        List<Order> orders = orderMapper.selectBatchIds(Arrays.asList(7, 1622975433488855042L));
        for (Order order : orders){
            log.info(order.toString());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 7);
        map.put("user_id", 1);
        List<Order> orders1 = orderMapper.selectByMap(map);
        for (Order order : orders1){
            log.info(order.toString());
        }
    }

    @Test
    public void addTest() {
        int result = orderMapper.insert(new Order(null, Long.parseLong("114514"), Long.parseLong("8848"), 233, new BigDecimal(66), 1, null, null, null,null));
        if (result > 0) {
            log.info("执行成功！");
        } else {
            log.info("执行失败！");
        }
    }

    @Test
    public void testUpdate() {
        orderMapper.updateById(new Order(Long.parseLong("1622923566666792962"), Long.parseLong("123"), null, null, null, null, null, null, null,null));
    }

    @Test
    public void testHappyLock() {
        //根据id查询再进行修改
        Order order = orderMapper.selectById(1622975433488855042L);
        order.setUserId(8208208820L);
        orderMapper.updateById(order);
    }

    /**
     * 关键点——分页查询的操作测试
     * @ClassName MpDemoApplicationTests
     * @author   cxb
     * @date  2023/2/7 23:25
     */
    @Test
    public void pageTest(){
        Page<Order> page = new Page<>(1,3);
        Page<Order> orderPage = orderMapper.selectPage(page, null);
        long pages = orderPage.getPages();
        log.info("pages = " + pages);
        long current = page.getCurrent();
        log.info("current = " + current);
        List<Order> records = page.getRecords();
        for (Order order : records){
            log.info("数据为" + order.toString());
        }
        long total = page.getTotal();
        log.info("total = " + total);
        boolean hasPrevious = page.hasPrevious();
        log.info("hasPrevious = " + hasPrevious);
        boolean hasNext = page.hasNext();
        log.info("hasNext = " + hasNext);
    }

    /**
     *测试逻辑删除的相关操作
     * @ClassName MpDemoApplicationTests
     * @author   cxb
     * @date  2023/2/8 10:52
     * @param []
     * @return void
     */
    @Test
    public void testDeletedLogic(){
        orderMapper.deleteById(1622975433488855043L);
    }

    @Test
    public void selectAll(){
        List<Order> orders = orderMapper.selectList(null);
        for (Order order : orders){
            log.info(order.toString());
        }
    }

    /**
     * 测试使用QueryWrapper的复杂条件的查询
     * @ClassName MpDemoApplicationTests
     * @author   cxb
     * @date  2023/2/8 11:01
     * @param []
     * @return void
     */
    @Test
    public void testWrapperSelect(){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", 123L);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        for (Order order : orders){
            log.info(order.toString());
        }
    }
}
