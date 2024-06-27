package com.threec.auth.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since 2024-04-25
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
//设置字体
@HeadFontStyle(fontHeightInPoints = 12, fontName = "Porsche Next")
// 内容字体设置成20
@ContentFontStyle(fontHeightInPoints = 11, fontName = "Porsche Next")
public class SysUserExcel {
    @ExcelProperty(value = "ID")
    private Integer id;
    @ExcelProperty(value = "用户名")
    private String userName;
    @ExcelProperty(value = "昵称")
    private String nickName;
    @ExcelProperty(value = "密码")
    private String password;
    @ExcelProperty(value = "邮箱")
    private String email;
    @ExcelProperty(value = "手机号")
    private String phoneNumber;
    @ExcelProperty(value = "0男,1女,2跨性别,3未知")
    private Integer sex;
    @ExcelProperty(value = "头像")
    private String avatar;
    @ExcelProperty(value = "启用（0未启用，1启用）")
    private Integer enabled;
    @ExcelProperty(value = "帐户未过期（0过期，1未过期）")
    private Integer accountNonExpired;
    @ExcelProperty(value = "凭证未过期(0过期，1未过期)")
    private Integer credentialsNonExpired;
    @ExcelProperty(value = "帐户未锁定(0锁定，1未锁定)")
    private Integer accountNonLocked;
    @ExcelProperty(value = "删除标志（0未删除，1已删除）")
    private Integer delFlag;
    @ExcelProperty(value = "创建人")
    private Integer createBy;
    @ExcelProperty(value = "创建时间")
    private Date createTime;
    @ExcelProperty(value = "更新人")
    private Integer updateBy;
    @ExcelProperty(value = "更新时间")
    private Date updateTime;
    @ExcelProperty(value = "删除人")
    private Integer delBy;
    @ExcelProperty(value = "删除时间")
    private Date delTime;

}