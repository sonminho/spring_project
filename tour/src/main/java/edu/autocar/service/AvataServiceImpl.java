package edu.autocar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import edu.autocar.dao.AvataDao;
import edu.autocar.domain.Avata;
import edu.autocar.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AvataServiceImpl implements AvataService {
	@Autowired
	AvataDao dao;

	Avata createAvata(String userId, MultipartFile file) throws Exception {
		if (file == null || file.isEmpty())
			return null;
		Avata avata = new Avata(userId, file.getBytes());
		avata.setImage(ImageUtil.thumb(avata.getImage()));
		return avata;
	}

	@Override
	public void create(String userId, MultipartFile file) throws Exception {
		Avata avata = createAvata(userId, file);
		if (avata != null)
			dao.insert(avata);
	}

	@Override
	public Avata getAvata(String userId) throws Exception {
		Avata avata = dao.findById(userId);
		if (avata == null) { // 아바타 이미지가 없는 경우
			// 디폴트 아바타 사용 - anonymous 사용자 미리 등록
			avata = dao.findById("anonymous");
		}
		return avata;
	}

	@Override
	public boolean update(String userId, MultipartFile file) throws Exception {
		Avata avata = createAvata(userId, file);
		if (avata == null)
			return true;
		if (dao.update(avata) == 1)
			return true;
		// 존재하지 않는 경우
		return dao.insert(avata) == 1;
	}

	@Override
	public boolean delete(String userId) throws Exception {
		return dao.delete(userId) == 1;
	}
}
