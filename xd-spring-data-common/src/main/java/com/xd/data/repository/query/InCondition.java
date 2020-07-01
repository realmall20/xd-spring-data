package com.xd.data.repository.query;

import java.util.Collection;

/**
 * @author xiaohei
 * @create 2020-07-01 下午3:14
 **/
public class InCondition implements ISegment {

    private FieldFunction field;

    private Collection<?> value;

    public InCondition(FieldFunction field, Collection<?> value) {
        this.field = field;
        this.value = value;
    }
}
