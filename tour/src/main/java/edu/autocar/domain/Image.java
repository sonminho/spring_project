package edu.autocar.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {
	private int imageId;	
	private int galleryId;	// 갤러리 ID
	private String originalName; // 원본 파일명
	private int fileSize; // 파일 크기
	private String mimeType; // 파일의 mime 타입
	private Date regDate; // 등록일
}
