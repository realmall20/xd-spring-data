package com.xd.data.repository.query;


/**
 * @author xiaohei
 * @create 2020-07-01 下午3:07
 **/
public class EqCondition implements ISegment {

    private FieldFunction field;

    private Object value;

    public EqCondition(FieldFunction field, Object value) {
        this.field = field;
        this.value = value;
    }
}
