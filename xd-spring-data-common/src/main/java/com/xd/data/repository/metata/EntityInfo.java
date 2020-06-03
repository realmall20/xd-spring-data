package com.xd.data.repository.metata;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * es index 相关的信息描述
 *
 * @author xiaohei
 * @create 2020-04-16 下午2:03
 **/
@Getter
public class EntityInfo<T> {
    /**
     * index 对应的实体名字
     */
    private final Class<T> entityType;
    /**
     * es 的文档名字
     */
    private final String indexName;
    /**
     * 对应的属性字段
     */
    private final List<EntityFieldInfo> fieldInfoList;

    private Map<String, EntityFieldInfo> columnFileMap=new HashMap<>();

    public EntityInfo(Class<T> entityType, String indexName, List<EntityFieldInfo> fieldInfoList){
        this.entityType=entityType;
        this.indexName=indexName;
        this.fieldInfoList=fieldInfoList;
        this.columnFileMap=this.fieldInfoList.stream().collect(Collectors.toMap(EntityFieldInfo::getColumn, a->a));
    }

    public String getSelectSql(){
       return fieldInfoList.stream().map(a->a.getColumn()).collect(Collectors.joining(","));
    }



}
