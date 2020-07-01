package com.xd.data.repository.query;

/**
 * 大于
 * @author xiaohei
 * @create 2020-07-01 下午4:30
 **/
public class GtCondition implements ISegment {
    private FieldFunction field;

    private Object value;

    public GtCondition(FieldFunction field, Object value) {
        this.field = field;
        this.value = value;
    }
}
