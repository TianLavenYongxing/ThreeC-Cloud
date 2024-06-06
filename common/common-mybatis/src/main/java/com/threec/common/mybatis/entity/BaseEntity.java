package com.threec.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    /**
     * 创建时间
     */
    private Integer createBy;
    /**
     * 创建人
     */
    private Date createTime;
    /**
     * 更新人
     */
    private Integer updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除时间
     */
    private Date delTime;
    /**
     * 删除人
     */
    private Integer delBy;
    /**
     * 删除标志（0已删除，1未删除）
     */
    private Integer delFlag;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", delFlag=" + delFlag +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", delTime=" + delTime +
                ", delBy=" + delBy +
                '}';
    }
}
