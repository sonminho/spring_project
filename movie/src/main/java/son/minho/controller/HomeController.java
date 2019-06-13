package son.minho.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import son.minho.domain.Genre;
import son.minho.domain.Movie;
import son.minho.domain.PageInfo;
import son.minho.domain.Score;
import son.minho.service.GenreService;
import son.minho.service.MovieService;
import son.minho.service.ScoreService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	GenreService genreService;
	
	@Autowired
	MovieService movieService;
	
	@Autowired
	ScoreService scoreService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam(value="page", defaultValue="1") int page, Model model) throws Exception {
		PageInfo<Movie> pi = movieService.getPage(page);
		List<Movie> list = pi.getList();
		model.addAttribute("pi", pi);
		
		for(Movie movie: list)
			System.out.println(movie);
		
		model.addAttribute("movieList", list);
		
		return "home";
	}
	
	@GetMapping("/create")
	public String getCreate(Model model) throws Exception {
		System.out.println(genreService.count());
		List<Genre> list = genreService.getGenre(0, 12);
		
		for(Genre g : list) {
			System.out.println(g);
		}
		
		model.addAttribute("genreList", list);
		return "create";
	}
	
	@PostMapping("/create")
	public String postCreate(Movie movie, Model model) throws Exception {
		System.out.println("현재 등록된 영화 갯수" + movieService.count());
		System.out.println(movie.toString());
		
		movieService.create(movie);
		
		return "redirect:/";
	}
	
	@GetMapping("/detail/{id}")
	@Transactional
	public String getDetail(@PathVariable("id") int id, Model model) throws Exception {
		Movie movie = movieService.getMovie(id);
		model.addAttribute("movie", movie);
		model.addAttribute("scoreList", scoreService.getScoreList(id));
		return "detail";
	}
	
	@GetMapping("/delete/{id}")
	@Transactional
	@ResponseBody
	public ResponseEntity<Map<String, String>> delete(@PathVariable("id")int movieId, Model model) throws Exception {
		System.out.println(movieId + "를 삭제할 예정");
		int result = movieService.delete(movieId);
		Map<String, String> map = new HashMap<String, String>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		if(result > 0) {
			map.put("result", "ok");			
		} else {
			map.put("result", "fail");			
		}
		
		return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK);		
	}
	
	@PostMapping("/score/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> score(@PathVariable("id")int movieId, @RequestBody Score score, Model model) throws Exception {
		System.out.println(movieId + "평점 요청" + score);
		Map<String, String> map = new HashMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		System.out.println("본 " + movieId +"영화에 " + scoreService.countMovieScore(movieId) + "개의 평점이 등록되었습니다.");
		
		try {
			map.put("result", "ok");
			scoreService.create(score);
			System.out.println("ok");
			return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK) ;
		} catch(Exception e) {
			map.put("result", "false");
			System.out.println("false");
			return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.BAD_REQUEST) ;
		}
	}
	
	@GetMapping("/score/delete/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteScore(@PathVariable("id")int scoreId, Model model) throws Exception {
		System.out.println(scoreId + "삭제 요청");
		Map<String, String> map = new HashMap<>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		try {
			map.put("result", "ok");
			System.out.println(scoreService.delete(scoreId));
			System.out.println("ok");
			return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.OK) ;
		} catch(Exception e) {
			map.put("result", "false");
			System.out.println("false");
			return new ResponseEntity<Map<String, String>>(map, headers, HttpStatus.BAD_REQUEST) ;
		}
	}
}
