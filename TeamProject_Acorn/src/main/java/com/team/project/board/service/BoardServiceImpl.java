package com.team.project.board.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.team.project.board.dao.BoardDao;
import com.team.project.board.dto.BoardCommentDto;
import com.team.project.board.dto.BoardDto;
import com.team.project.exception.CanNotDeleteException;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDao boardDao;
	
	//한 페이지에 나타낼 Row의 갯수
	static final int PAGE_ROW_COUNT=5;
	//하단 디스플레이 페이지 갯수
	static final int PAGE_DISPLAY_COUNT=5;

	@Override
	public void getList(HttpServletRequest request) {

		//검색과 관련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");

		//BoardDto 객체 생성 (select 할때 필요한 정보를 담기 위해)
		BoardDto dto=new BoardDto();

		if(keyword != null) {//검색 키워드가 전달된 경우
			if(condition.equals("titlecontent")) {//제목+내용 검색
				dto.setTitle(keyword);
				dto.setContent(keyword);
			}else if(condition.equals("title")) {//제목 검색
				dto.setTitle(keyword);
			}else if(condition.equals("writer")) {//작성자 검색
				dto.setWriter(keyword);
			}
			//request 에 검색 조건과 키워드 담기
			request.setAttribute("condition", condition);
			/*
			 *  검색 키워드에는 한글이 포함될 가능성이 있기 때문에
			 *  링크에 그대로 출력가능하도록 하기 위해 미리 인코딩을 해서
			 *  request 에 담아준다.
			 */
			String encodedKeyword=null;
			try {
				encodedKeyword=URLEncoder.encode(keyword, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//인코딩된 키워드와 인코딩 안된 키워드를 모두 담는다.
			request.setAttribute("encodedKeyword", encodedKeyword);
			request.setAttribute("keyword", keyword);
		}		

		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;

		//전체 row 의 갯수를 읽어온다.
		int totalRow=boardDao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		// startRowNum 과 endRowNum 을 BoardDto 객체에 담고 
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);

		// startRowNum 과 endRowNum 에 해당하는 카페글 목록을 select 해 온다.
		List<BoardDto> list=boardDao.getList(dto);

		//view 페이지에서 필요한 값을 request 에 담고 
		request.setAttribute("list", list);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("totalPageCount", totalPageCount);	
		//전체 글의 갯수도 request 에 담는다.
		request.setAttribute("totalRow", totalRow);			
	}

	@Override
	public void saveContent(BoardDto dto) {
		boardDao.insert(dto);
		
	}

	@Override
	public void getDetail(HttpServletRequest request) {
		//파라미터로 전달되는 글번호
		int num=Integer.parseInt(request.getParameter("num"));
		
		//검색과 고나련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		
		//BoardDto 객체 생성 (select 할 때 필요한 정보를 담기 위하여)
		BoardDto dto=new BoardDto(); 
		
		if(keyword !=null) {//검색 키워드 전달된 경우
			if(condition.equals("titlecontent")) {//제목+내용
				dto.setTitle(keyword);
				dto.setContent(keyword);
			}else if(condition.equals("title")) {//제목
				dto.setTitle(keyword);
			}else if(condition.equals("writer")) {//작성자
				dto.setWriter(keyword);
			}
			//request에 검색 조건과 키워드 담기
			request.setAttribute("condition", condition);
			/*
			   	검색 키워드에는 한글이 포함될 가능성이 있기 때문에
			   	링크에 그대로 출력가능하도록 하기 위해 미리 인코딩을 해서
			   	request 에 담아준다.
			 */
			String encodedKeyword=null;
			try {
				encodedKeyword=URLEncoder.encode(keyword, "utf-8"); 
			}catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//인코딩된 키워드와 인코딩이 안 된 키워드를 모두 담는다.
			request.setAttribute("encodedKeyword", encodedKeyword);
			request.setAttribute("keyword", keyword);
		}
		//BoardDto에 글번호를 담기
		dto.setNum(num);
		//조회수 1 증가 시키기
		boardDao.addViewCount(num);
		//글정보를 얻어오기
		BoardDto dto2=boardDao.getData(dto);
		//request에 글 정보 담기
		request.setAttribute("dto", dto2);
	}

	@Override
	public void deleteContent(int num, HttpServletRequest request) {
		String id=(String)request.getSession().getAttribute("id");
		String writer=boardDao.getData(num).getWriter();
		if(!id.equals(writer)) {
			throw new CanNotDeleteException();
		}
		boardDao.delete(num);
	}

	@Override
	public void getUpdateData(ModelAndView mView, int num) {
		//수정할 글번호를 이용해서 수정할 글 정보를 얻어오기
		BoardDto dto=boardDao.getData(num);
		//ModelAndView 객체에 담는다
		mView.addObject("dto", dto);
	}

	@Override
	public void updateContent(BoardDto dto) {
		//BoardDao 객체를 이용해서 원글을 수정 반영
		boardDao.update(dto);
	}

	@Override
	public void saveComment(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComment(int num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComment(BoardCommentDto dto) {
		// TODO Auto-generated method stub
		
	}
}