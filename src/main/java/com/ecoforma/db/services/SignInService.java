package com.ecoforma.db.services;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.SignInMapper;
import org.apache.ibatis.session.SqlSession;

public class SignInService {
    public String getSessionType(String login, String password) {
        String sessionType;
        try (SqlSession session = DbSession.startSession()) {
            SignInMapper mapper = session.getMapper(SignInMapper.class);
            sessionType = mapper.getSessionType(login, password);
        }
        return sessionType;
    }
}
