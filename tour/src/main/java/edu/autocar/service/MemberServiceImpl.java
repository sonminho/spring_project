package edu.autocar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.MemberDao;
import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;
import edu.autocar.util.SHA256Util;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberServiceImpl implements MemberService {
	final static private int PER_PAGE_COUNT = 2;

	@Autowired
	MemberDao dao;

	@Autowired
	AvataService avataService;
	
	@Override
	public PageInfo<Member> getPage(int page) throws Exception {
		int start = (page - 1) * PER_PAGE_COUNT;
		int end = start + PER_PAGE_COUNT;
		int totalCount = dao.count();
		List<Member> list = dao.getPage(start + 1, end);

		return new PageInfo<>(totalCount, (int) Math.ceil(totalCount / (double) PER_PAGE_COUNT), page, PER_PAGE_COUNT,
				list);
	}

	@Override
	public Member getMember(String userId) throws Exception {
		Member member = dao.findById(userId);
		System.out.println("요청한 회원정보 : " + member);
		return member;
	}

	@Override
	@Transactional
	public void create(Member member, MultipartFile file) throws Exception {
		String salt = SHA256Util.generateSalt();
		String encPassword = SHA256Util.getEncrypt(member.getPassword(), salt);
		member.setSalt(salt);
		member.setPassword(encPassword);

		dao.insert(member);
		avataService.create(member.getUserId(), file);
	}

	@Transactional
	@Override
	public boolean updateByAdmin(Member member, MultipartFile file) throws Exception {
		if (!checkAdminPassword(member.getPassword()))
			return false;
		
		if(dao.updateByAdmin(member) != 1)
			return false;
		
		//아바타 수정
		avataService.update(member.getUserId(), file);
		return true;
	}

	private boolean checkAdminPassword(String password) throws Exception {
		Member admin = dao.findById("admin");
		String adminPassword = admin.getPassword();
		password = SHA256Util.getEncrypt(password, // 입력받은 비밀번호
				admin.getSalt()); // admin의 salt
		
		return adminPassword.equals(password);
	}

	@Transactional
	@Override
	public boolean update(Member member, MultipartFile file) throws Exception {
		System.out.println("서비스에 요청 " + member);
		String password = null;
		
		if ((password = checkPassword(member.getUserId(), member.getPassword()).getPassword())== null) {
			return false;
		}
		member.setPassword(password);
		
		if(dao.update(member) == 0)
			return false;
		
		return avataService.update(member.getUserId(), file);
	}

	@Transactional
	@Override
	public boolean delete(String userId, String password) throws Exception {
		System.out.println("서비스에서 호출 id = " + userId + ", pw = " + password);

		if (!checkAdminPassword(password))
			return false;

		return dao.delete(userId) == 1;
	}

	@Override
	public Member checkPassword(String userId, String password) throws Exception {
		Member user = dao.findById(userId);
		
		if (user != null) {
			// 사용자 ID가 존재하는 경우
			password = SHA256Util.getEncrypt(password, user.getSalt());
			System.out.println("사용자 비번" + password);
			System.out.println("DB 내 비번" + user.getPassword());
			
			if (password.equals(user.getPassword()))
				return user;
		}

		// ID가 없거나 비밀번호가 다른 경우
		return null;
	}
}
