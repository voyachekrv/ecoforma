package com.ecoforma.db.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RegistrationDataMapper {
    @Select("SELECT name FROM role WHERE ID = (SELECT role_ID FROM registrationData WHERE (login = #{login} AND password = #{password}))")
    String getSessionType(@Param("login") String login, @Param("password") String password);
}
