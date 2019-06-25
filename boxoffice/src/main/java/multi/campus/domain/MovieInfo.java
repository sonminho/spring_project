package multi.campus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor	
public class MovieInfo {
	int movieCd; // 영화 코드
	String movieNm; // 영화 제목
	int prdtYear; // 제작 연도
	String openDt; // 개봉일
	String nationNm; // 제작 국가
	String directorNm; // 감독
	String imageUrl; // 썸네일 이미지
}
