package cn.net.gxht.app.yjdPlatform.user.dao;

import cn.net.gxht.app.yjdPlatform.user.entity.AppUser;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/9/18.
 */
public interface UserDao {
    Integer insertAppUser(@Param(value="password") String password, @Param(value = "phone") String phone);
    Integer findHasSameName(String name);
    AppUser findUserByName(String name);
    AppUser findUserByPhone(String phone);
    Integer insertToken(@Param(value="tokenValue") String tokenValue,@Param(value="phone") String phone);
}
