package com.xd.data.repository.query;

import com.xd.data.repository.enums.Like;
import lombok.AllArgsConstructor;

/**
 * 模糊查询，只有string 查询，其它的不行
 * @author xiaohei
 * @create 2020-07-01 下午3:28
 **/
@AllArgsConstructor
public class LikeCondition implements ISegment {

    private FieldFunction field;

    private String value;

    private Like like;

}
