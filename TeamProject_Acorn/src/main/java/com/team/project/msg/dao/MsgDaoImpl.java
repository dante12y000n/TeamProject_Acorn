package com.team.project.msg.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team.project.msg.dto.MsgDto;
import com.team.project.users.dto.UsersDto;

@Repository
public class MsgDaoImpl implements MsgDao{
	@Autowired
	private SqlSession session;

	@Override
	public int getCount(MsgDto dto) {
		int count=session.selectOne("msg.getCount", dto);
		return count;
	}
	
	@Override
	public int getSentCount(MsgDto dto) {
		int count=session.selectOne("msg.getSentCount", dto);
		return count;
	}
	
	@Override
	public int getSavedCount(MsgDto dto) {
		int count=session.selectOne("msg.getSavedCount", dto);
		return count;
	}

	@Override
	public List<MsgDto> getList(MsgDto dto) {
		List<MsgDto> list=session.selectList("msg.getList", dto);
		return list;
	}
	
	@Override
	public List<MsgDto> getSentList(MsgDto dto) {
		List<MsgDto> list=session.selectList("msg.getSentList", dto);
		return list;
	}

	@Override
	public List<MsgDto> getSavedList(MsgDto dto) {
		List<MsgDto> list=session.selectList("msg.getSavedList", dto);
		return list;
	}
	
	@Override
	public MsgDto getData(MsgDto dto) {
		dto=session.selectOne("msg.getData", dto);
		return dto;
	}

	@Override
	public void checkRead(MsgDto dto) {
		session.update("msg.checkRead", dto);
	}

	@Override
	public void checkSaved(int num) {
		session.update("msg.checkSaved", num);
	}
	
	@Override
	public void sendMsg(MsgDto dto) {
		session.insert("msg.sendMsg", dto);
	}

	@Override
	public int getUnreadCount(MsgDto dto) {
		int count=session.selectOne("msg.getUnreadCount", dto);
		return count;
	}

	@Override
	public void recDel(int num) {
		session.update("msg.recDel", num);
	}

	@Override
	public void sendDel(int num) {
		session.update("msg.sendDel", num);
	}

	@Override
	public void savedDel(int num) {
		session.update("msg.savedDel", num);
	}

	@Override
	public void restoreSaved(int num) {
		session.update("msg.restoreSaved", num);
	}

	@Override
	public void restoreSavedDel(int num) {
		session.update("msg.restoreSavedDel", num);
	}

	@Override
	public List<UsersDto> getIdList(MsgDto dto) {
		List<UsersDto> list=session.selectList("msg.getIdList", dto);
		return list;
	}

}
