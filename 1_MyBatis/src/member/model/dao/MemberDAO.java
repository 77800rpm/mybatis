package member.model.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import member.model.exception.MemberException;
import member.model.vo.Member;

public class MemberDAO { // DB에 보낼 쿼리에 연결

	public Member selectMember(SqlSession session, Member m) throws MemberException {
		// select * from member where user_id=? and user_pwd=?
		// --> 1개 또는 0개의 행이 담겨 있는 ResultSet
		
		// selectOne(String arg0)
		// selectOne(String arg0, Object arg1)
		//	첫 번째 매개변수 String arg0 : 연결될 매퍼의 쿼리 id
		//	두 번째 매개변수 Object arg1 : mapper에 전달할 데이터
		// -> 쿼리에 넘길 데이터가 있느냐 없느냐에 따라 알맞게 사용
		// -> 쿼리에 넘길 데이터 없으면 매개변수 1개, 아니면 2개
		
		Member member = session.selectOne("memberMapper.loginMember", m); // 제네릭 T가 반환값이라 내가 정의한 대로 바꿀 수 있음
		//System.out.println(member);
		
		if(member == null) {
			session.close();
			throw new MemberException("로그인에 실패하였습니다.");
		}
		
		return member;
	}

	public void insertMember(SqlSession session, Member m) throws MemberException {
		int result = session.insert("memberMapper.insertMember", m);
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원가입에 실패하였습니다.");
		}		
	}
	
	public void updateMember(SqlSession session, Member m) throws MemberException {
	      int result = session.update("memberMapper.updateMember", m);
	      if(result <= 0) {
	         session.rollback();
	         session.close();
	         throw new MemberException("개인정보수정에 실패하였습니다.");
	      }
	      
	   }
	
	public void updatePwd(SqlSession session, HashMap<String, String> map) throws MemberException {
		int result = session.update("memberMapper.updatePwd", map);
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("비밀번호 수정에 실패하였습니다.");
		}		
		
	}
	
	public void deleteMember(SqlSession session, String userId) throws MemberException {
		int result = session.update("memberMapper.deleteMember", userId);
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원 탈퇴에 실패하였습니다.");
		}		
	}

}
