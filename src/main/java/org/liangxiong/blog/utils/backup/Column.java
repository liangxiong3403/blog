package org.liangxiong.blog.utils.backup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author liangxiong
 * @Description
 */
@Getter
@AllArgsConstructor
@ToString
public class Column {

    private String name;

    private String typeName;

    private int dataType;


}
