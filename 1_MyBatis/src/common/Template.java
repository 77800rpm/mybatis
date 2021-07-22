package common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Template { // JDBCTemplate : Connection 객체 생성, 공통 메소드 정의(close, rollback, commit)
	public static SqlSession getSqlSession() {
		SqlSession session = null;
		
		// SqlSession <----------- SqlSessionFactory <------------ SqlSessionFactoryBuilder
		//				openSession()				 build(inputStream)
				
		try {
			/*
			 * SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder(); InputStream
			 * inputStream = Resources.getResourceAsStream("/mybatis-config.xml"); // 반환값
			 * InputStream SqlSessionFactory ssf = ssfb.build(inputStream); // session =
			 * ssf.openSession(); session = ssf.openSession(false); // 자동 커밋 안 하게 하겠다
			 */			
			
			InputStream inputStream = Resources.getResourceAsStream("/mybatis-config.xml");
			session = new SqlSessionFactoryBuilder().build(inputStream).openSession(false);			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return session;
	}

}
