package com.threec.common.mybatis.vo;

import java.util.List;

public class DictVO {
    private Long id;
    private String code;
    private String name;
    private String param;
    private List<DictVO> childDicts;

    public DictVO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(final String param) {
        this.param = param;
    }

    public List<DictVO> getChildDicts() {
        return this.childDicts;
    }

    public void setChildDicts(final List<DictVO> childDicts) {
        this.childDicts = childDicts;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DictVO)) {
            return false;
        } else {
            DictVO other = (DictVO) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71:
                {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label71;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label71;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                label57:
                {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label57;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label57;
                    }

                    return false;
                }

                Object this$param = this.getParam();
                Object other$param = other.getParam();
                if (this$param == null) {
                    if (other$param != null) {
                        return false;
                    }
                } else if (!this$param.equals(other$param)) {
                    return false;
                }

                Object this$childDicts = this.getChildDicts();
                Object other$childDicts = other.getChildDicts();
                if (this$childDicts == null) {
                    if (other$childDicts == null) {
                        return true;
                    }
                } else if (this$childDicts.equals(other$childDicts)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DictVO;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $param = this.getParam();
        result = result * 59 + ($param == null ? 43 : $param.hashCode());
        Object $childDicts = this.getChildDicts();
        result = result * 59 + ($childDicts == null ? 43 : $childDicts.hashCode());
        return result;
    }

    public String toString() {
        return "DictVO(id=" + this.getId() + ", code=" + this.getCode() + ", name=" + this.getName() + ", param=" + this.getParam() + ", childDicts=" + this.getChildDicts() + ")";
    }
}
