package com.cabin.controller;

import com.cabin.entity.Routing;
import com.cabin.entity.bo.AddRoutingBo;
import com.cabin.mapper.jpa.RoutingRepository;
import com.cabin.utils.response.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/29 10:03
 */
@RestController
@RequestMapping("/routing")
public class RoutingController {
    @Autowired
    private RoutingRepository repository;

    @GetMapping("/all")
    public Result<List<Routing>> getRoutings() {
        List<Routing> routings = repository.getRoutingsByDeleted(0);
        return Result.success(routings, "所有路由");
    }

    @GetMapping("/{id}")
    public Result<Routing> getRoutingById(@PathVariable Long id) {
        Routing routings = repository.getRoutingByIdAndDeleted(id, 0);
        return Result.success(routings, "指定路由");
    }

    @PostMapping("/")
    public Result<Routing> addRouting(@RequestBody AddRoutingBo bo) {
        Routing exist = repository.getRoutingByPathAndDeleted(bo.getPath(), 0);
        if (exist != null) {
            return Result.serverFail("该路由已存在");
        }
        Routing routingByTitle = repository.getRoutingByTitleAndDeleted(bo.getTitle(), 0);
        if (routingByTitle != null) {
            return Result.serverFail("该路由已存在");

        }
        Routing routing = new Routing();
        BeanUtils.copyProperties(bo, routing);
        Routing insert = repository.save(routing);
        return Result.success(insert, "新增路由成功");
    }

    @PostMapping("/list")
    public Result<List<Routing>> addRoutings(@RequestBody List<AddRoutingBo> bos) {
        for (AddRoutingBo bo : bos) {
            Routing exist = repository.getRoutingByPathAndDeleted(bo.getPath(), 0);
            if (exist != null) {
                continue;
            }
            Routing routingByTitle = repository.getRoutingByTitleAndDeleted(bo.getTitle(), 0);
            if (routingByTitle != null) {
                continue;

            }
            Routing routing = new Routing();
            BeanUtils.copyProperties(bo, routing);
            repository.save(routing);
        }
        List<Routing> routings = repository.getRoutingsByDeleted(0);
        return Result.success(routings, "新增路由成功");
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delRoutingById(@PathVariable Long id) {
        Boolean exist = repository.existsRoutingByIdAndDeleted(id, 0);
        if (!exist) {
            return Result.serverFail("没有该路由");
        }
        Routing routingById = repository.getRoutingByIdAndDeleted(id, 0);
        routingById.setDeleted(1);
        repository.save(routingById);
        return Result.success(true, "删除成功");
    }
}
