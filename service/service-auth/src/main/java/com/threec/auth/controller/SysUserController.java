package com.threec.auth.controller;


import com.threec.auth.excel.SysUserExcel;
import com.threec.auth.service.SysUserService;
import com.threec.common.mybatis.page.PageData;
import com.threec.common.mybatis.utils.ExcelUtils;
import com.threec.common.mybatis.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import com.threec.common.mybatis.constant.Constant;

import java.util.List;
import java.util.Map;
import com.threec.auth.dto.SysUserDTO;


/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since 2024-04-25
 */
@RestController
@RequestMapping("/sysuser")
@Api(tags = "系统用户表")
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public void setRepository(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    public R<PageData<SysUserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {

        PageData<SysUserDTO> page = sysUserService.page(params);

        return new R<PageData<SysUserDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public R<SysUserDTO> get(@PathVariable("id") String id) {
        SysUserDTO data = sysUserService.get(id);

        return new R<SysUserDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    public R<Object> save(@Valid @RequestBody SysUserDTO dto) {

        sysUserService.save(dto);

        return new R<>();
    }

    @PutMapping
    @ApiOperation("修改")
    public R<Object> update(@RequestBody SysUserDTO dto) {

        sysUserService.update(dto);

        return new R<>();
    }

    @DeleteMapping
    @ApiOperation("删除")
    public R<Object> delete(@RequestBody String[] ids) {

        sysUserService.delete(ids);

        return new R<>();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysUserDTO> list = sysUserService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SysUserExcel.class);
    }

}