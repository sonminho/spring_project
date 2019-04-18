package edu.autocar.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsg {
	private String result; // 처리 결과 문자열
	private String message; // 결과 메시지 문자열

	public static ResponseEntity<ResultMsg> response(String result, String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<>(new ResultMsg(result, message), headers, HttpStatus.OK);
	}
}