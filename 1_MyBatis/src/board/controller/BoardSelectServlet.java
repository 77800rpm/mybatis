package board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.exception.BoardException;
import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardSelectServlet
 */
@WebServlet("/selectOne.bo")
public class BoardSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		try {			
			Board board = new BoardService().selectBoard(bId);
			
//			System.out.println(board);
			
			int rCount = 0;
			if(!board.getReplyList().isEmpty()) {
				rCount = board.getReplyList().size();
			}
			
			request.setAttribute("bId", bId);
			request.setAttribute("rCount", rCount);
			request.setAttribute("b", board);
			request.getRequestDispatcher("WEB-INF/views/board/boardDetail.jsp").forward(request, response);			
			
			
		} catch (BoardException e) {
	         request.setAttribute("msg", e.getMessage());
	         request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
