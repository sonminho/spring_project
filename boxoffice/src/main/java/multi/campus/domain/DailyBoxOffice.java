package multi.campus.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyBoxOffice {
	int movieCd; // 영화 코드
	String movieNm; // 영화 제목
	int rank; // 순위
	int rankInten; // 전일 대비 순위
	String rankOldAndNew; // 랭킹 진입 여부	
	String openDt; // 영화 개봉일
	long salesAcc; // 누적 매출액
	int audiCnt; // 해당일의 관객수
	int audiAcc; // 누적 관객수
}
