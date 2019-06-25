package multi.campus.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import multi.campus.dao.DailyBoxOfficeDao;
import multi.campus.dao.MovieInfoDao;
import multi.campus.domain.DailyBoxOffice;
import multi.campus.domain.MovieActor;
import multi.campus.domain.MovieGenre;
import multi.campus.domain.MovieInfo;

@Repository
public class MovieInfoServiceImpl implements MovieInfoService {

	@Autowired
	DailyBoxOfficeDao dailyBoxOfficeDao;

	@Autowired
	MovieInfoDao movieInfoDao;

	static final String key = "125cacae52fd2d0be4cbdd4df53e1b3d";

	@Override
	@Transactional
	public JSONArray getKobisDailyMovieList(String targetDt) throws Exception {
		String targetUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key="
				+ key + "&targetDt=" + targetDt;
		URL url = new URL(targetUrl);
		JSONArray jsonMovieList = null;

		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inLine;
			StringBuilder sb = new StringBuilder();
			while ((inLine = br.readLine()) != null) {
				sb.append(inLine + "\n");
			}
			System.out.println(sb);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) jsonParser.parse(sb.toString());
			JSONObject jsonResult = (JSONObject) jsonResponse.get("boxOfficeResult");
			jsonMovieList = (JSONArray) jsonResult.get("dailyBoxOfficeList");

			// 일간 박스오피스 리스트를 초기화하고
			deleteAllBoxOffce();

			// 영화진흥원으로부터 받아온 데이터를 DB에 추가한다.
			for (int i = 0; i < jsonMovieList.size(); i++) {
				JSONObject movie = (JSONObject) jsonMovieList.get(i);
				System.out.println(movie.toString());
				DailyBoxOffice dailyBoxOffice = new DailyBoxOffice();
				dailyBoxOffice.setMovieCd(Integer.parseInt(movie.get("movieCd").toString()));
				dailyBoxOffice.setMovieNm(movie.get("movieNm").toString());
				dailyBoxOffice.setRank(Integer.parseInt(movie.get("rank").toString()));
				dailyBoxOffice.setRankInten(Integer.parseInt(movie.get("rankInten").toString()));
				dailyBoxOffice.setRankOldAndNew(movie.get("rankOldAndNew").toString());
				dailyBoxOffice.setOpenDt(movie.get("openDt").toString());
				dailyBoxOffice.setSalesAcc(Long.parseLong(movie.get("salesAcc").toString()));
				dailyBoxOffice.setAudiCnt(Integer.parseInt(movie.get("audiCnt").toString()));
				dailyBoxOffice.setAudiAcc(Integer.parseInt(movie.get("audiAcc").toString()));

				createDailyBoxOffice(dailyBoxOffice);
				getMovieInfo(dailyBoxOffice.getMovieCd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonMovieList;
	}

	@Override
	public MovieInfo getMovieInfo(int movieCd) throws Exception {
		String targetUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?movieCd="
				+ movieCd + "&key=" + key;
		URL url = new URL(targetUrl);
		MovieInfo movieInfo = new MovieInfo();
		
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inLine;
			StringBuilder sb = new StringBuilder();
			while ((inLine = br.readLine()) != null) {
				sb.append(inLine + "\n");
			}
			System.out.println("*" + sb);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) jsonParser.parse(sb.toString());
			JSONObject jsonResult = (JSONObject) jsonResponse.get("movieInfoResult");
			JSONObject jsonMovieInfo = (JSONObject) jsonResult.get("movieInfo");
			// movieCd, movieNm, prdtYear, openDt, nation, director
			// Array : genres.genreNm, actors.peopleNm
			System.out.println("**************************");
			JSONObject jsonNation = (JSONObject) ((JSONArray) jsonMovieInfo.get("nations")).get(0);
			JSONObject jsonDirector = (JSONObject) ((JSONArray) jsonMovieInfo.get("directors")).get(0);
			System.out.println(jsonMovieInfo.get("movieCd") + ", " + jsonMovieInfo.get("movieNm") + ", ("
					+ jsonMovieInfo.get("prdtYear") + "), " + jsonNation.get("nationNm") + ", "
					+ jsonDirector.get("peopleNm") + " 제작" + jsonMovieInfo.get("openDt") + " 개봉");
			
			movieInfo.setMovieCd(Integer.parseInt(jsonMovieInfo.get("movieCd").toString()));
			movieInfo.setMovieNm(jsonMovieInfo.get("movieNm").toString());
			movieInfo.setPrdtYear(Integer.parseInt(jsonMovieInfo.get("prdtYear").toString()));
			movieInfo.setOpenDt(jsonMovieInfo.get("openDt").toString());
			movieInfo.setNationNm(jsonNation.get("nationNm").toString());
			movieInfo.setDirectorNm(jsonDirector.get("peopleNm").toString());
			System.out.println("영화 정보 : " + movieInfo);
			
			createMovieInfo(movieInfo);
			
			JSONArray jsonArrayGenre = (JSONArray) jsonMovieInfo.get("genres");
			JSONArray jsonArrayActor = (JSONArray) jsonMovieInfo.get("actors");
			for (int i = 0; i < jsonArrayGenre.size(); i++) {
				System.out.println(((JSONObject) jsonArrayGenre.get(i)).get("genreNm"));
				createMovieGenre(new MovieGenre(movieCd, ((JSONObject) jsonArrayGenre.get(i)).get("genreNm").toString()));
			}
			for (int i = 0; i < jsonArrayActor.size(); i++) {
				System.out.println(((JSONObject) jsonArrayActor.get(i)).get("peopleNm"));
				createMovieActor(new MovieActor(movieCd, ((JSONObject) jsonArrayActor.get(i)).get("peopleNm").toString()));
			}
			System.out.println("**************************");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return movieInfo;
	}

	@Override
	public List<DailyBoxOffice> getAllDailyBoxOffice() throws Exception {
		return dailyBoxOfficeDao.getAllDailyBoxOffice();
	}

	@Override
	public int createDailyBoxOffice(DailyBoxOffice dailyBoxOffice) throws Exception {
		return dailyBoxOfficeDao.insert(dailyBoxOffice);
	}

	@Override
	public int createMovieInfo(MovieInfo movieInfo) throws Exception {
		return movieInfoDao.insert(movieInfo);
	}

	@Override
	public int deleteAllBoxOffce() throws Exception {
		return dailyBoxOfficeDao.deleteAllBoxOffce();
	}

	@Override
	public int createMovieGenre(MovieGenre movieGenre) throws Exception {
		return movieInfoDao.insertGenre(movieGenre);
	}

	@Override
	public int createMovieActor(MovieActor movieActor) throws Exception {
		return movieInfoDao.insertActor(movieActor);
	}

}
