package net.xdclass.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName UserFamilyInfo
 * @Description TODO
 * @Date 2022/3/5 14:53
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserFamilyMemberInfo {
    private String name;
    private String age;
    private String relationship;
}
