package com.threec.common.mybatis.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel("分页数据")
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("总记录数")
    private int total;
    @ApiModelProperty("列表数据")
    private List<T> list;

    public PageData(List<T> list, long total) {
        this.list = list;
        this.total = (int)total;
    }

    public int getTotal() {
        return this.total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

    public void setList(final List<T> list) {
        this.list = list;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageData)) {
            return false;
        } else {
            PageData<?> other = (PageData)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getTotal() != other.getTotal()) {
                return false;
            } else {
                Object this$list = this.getList();
                Object other$list = other.getList();
                if (this$list == null) {
                    if (other$list != null) {
                        return false;
                    }
                } else if (!this$list.equals(other$list)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageData;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getTotal();
        Object $list = this.getList();
        result = result * 59 + ($list == null ? 43 : $list.hashCode());
        return result;
    }

    public String toString() {
        return "PageData(total=" + this.getTotal() + ", list=" + this.getList() + ")";
    }
}
