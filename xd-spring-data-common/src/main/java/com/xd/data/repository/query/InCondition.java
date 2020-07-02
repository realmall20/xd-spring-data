package com.xd.data.repository.query;

import com.xd.data.repository.function.FieldFunction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;

/**
 * @author xiaohei
 * @create 2020-07-01 下午3:14
 **/
@Getter
@AllArgsConstructor
public class InCondition implements ISegment {

    private FieldFunction field;

    private Collection<?> value;


}
