package board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import board.model.exception.BoardException;
import board.model.vo.Board;
import board.model.vo.PageInfo;

public class BoardDAO {

	public int getListCount(SqlSession session) throws BoardException {
		int listCount = session.selectOne("boardMapper.getListCount");

		if(listCount <= 0) {
			session.close();
			throw new BoardException("게시물 조회에 실패하였습니다");
		}
		
		return listCount;
	}

	public ArrayList<Board> selectBoardList(SqlSession session, PageInfo pi) throws BoardException {
		// mybatis에서의 페이징 처리 : m개의 게시글을 건너뛰고 n개의 게시글을 가져오겠다
		int offset = pi.getBoardLimit() * (pi.getCurrentPage() - 1); // m
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit()); // MyBatis에서 페이징 처리를 하기 위한 클래스

		ArrayList<Board> list = (ArrayList)session.selectList("boardMapper.selectBoardList", null, rowBounds);
		//System.out.println(list);
		
		if(list == null) {
			session.close();
			throw new BoardException("게시글 조회에 실패했습니다.");
		}
		
		return list;
	}

	public Board selectBoard(SqlSession session, int bId) throws BoardException {
		Board board = session.selectOne("boardMapper.selectBoard", bId);
		
		if(board == null) {
			session.close();
			throw new BoardException("게시글 상세 보기에 실패하였습니다.");
		}
		
		return board;
	}
	
	public void updateCount(SqlSession session, int bId) throws BoardException {
		int result = session.update("boardMapper.updateCount", bId);
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new BoardException("게시글 조회수 증가에 실패하였습니다.");
		}
	}

	public int getSearchResultListCount(SqlSession session, HashMap<String, String> map) throws BoardException {
		int listCount = session.selectOne("boardMapper.selectSearchResultCount", map);
		
		if(listCount <= 0) {
			session.close();
			throw new BoardException("검색 결과 카운트에 실패하였습니다.");
		}
		
		return listCount;
	}

	public ArrayList<Board> selectSearchResultList(SqlSession session, HashMap<String, String> map, PageInfo pi) throws BoardException {
		
		int offset = pi.getBoardLimit() * (pi.getCurrentPage() - 1);
		
		RowBounds rb = new RowBounds(offset, pi.getBoardLimit());
		
		ArrayList<Board> list = (ArrayList)session.selectList("boardMapper.selectSearchResultList", map, rb);
		
		if(list == null) {
			session.close();
			throw new BoardException("검색 결과 리스트 조회에 실패하였습니다.");
		}
		
		return list;
	}

}
