package edu.autocar.service;

import org.springframework.web.multipart.MultipartFile;

import edu.autocar.domain.Member;
import edu.autocar.domain.PageInfo;

public interface MemberService {
	PageInfo<Member> getPage(int page) throws Exception;
	
	Member getMember(String userId) throws Exception;
	
	void create(Member member, MultipartFile file) throws Exception;
	
	boolean update(Member member, MultipartFile file) throws Exception;
	
	boolean updateByAdmin(Member member, MultipartFile file) throws Exception;
	
	boolean delete(String userId, String password) throws Exception;

	Member checkPassword(String userId, String password) throws Exception;
}
