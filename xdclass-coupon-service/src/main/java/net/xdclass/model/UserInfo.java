package net.xdclass.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Date 2022/3/5 14:51
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo {
    private String name;
    private String age;
    private String sex;

    private List<UserFamilyMemberInfo> userFamilyMemberInfos;

}
