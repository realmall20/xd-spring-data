package com.xd.data.repository.query;

import com.xd.data.repository.enums.Keyword;
import com.xd.data.repository.function.FieldFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 大于
 * @author xiaohei
 * @create 2020-07-01 下午4:30
 **/
@AllArgsConstructor
@Getter
public class KVCondition implements ISegment {
    private FieldFunction field;
    private Object value;
    private Keyword operate;
}
