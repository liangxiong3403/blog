package org.liangxiong.blog.utils.backup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author liangxiong
 * @Description
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FK {

    private String column;

    private Table referenceTable;

    private String referencePK;


}
