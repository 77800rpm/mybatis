package board.model.service;

import org.apache.ibatis.session.SqlSession;

import board.model.dao.BoardDAO;
import board.model.exception.BoardException;
import board.model.vo.Board;
import board.model.vo.PageInfo;

import static common.Template.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardService {

	public int  getListCount() throws BoardException {
		SqlSession session = getSqlSession();
		int listCount = new BoardDAO().getListCount(session);
		session.close();
		
		return listCount;
	}

	public ArrayList<Board> selectBoardList(PageInfo pi) throws BoardException {
		SqlSession session = getSqlSession();
		ArrayList<Board> list = new BoardDAO().selectBoardList(session, pi);
		session.close();
		return list;
	}

	public Board selectBoard(int bId) throws BoardException {
		SqlSession session = getSqlSession();

		BoardDAO dao = new BoardDAO();
		dao.updateCount(session, bId);
		
		Board board = new BoardDAO().selectBoard(session, bId);
		
		session.commit();
		session.close();
		
		return board;
	}

	public int getSearchResultListCount(HashMap<String, String> map) throws BoardException {
		SqlSession session = getSqlSession();

		int listCount = new BoardDAO().getSearchResultListCount(session, map);
		
		session.close();
		
		return listCount;
	}

	public ArrayList<Board> selectSearchResultList(HashMap<String, String> map, PageInfo pi) throws BoardException {
		SqlSession session = getSqlSession();
		
		ArrayList<Board> list = new BoardDAO().selectSearchResultList(session, map, pi);
		
		session.close();
		
		return list;
	}


}
