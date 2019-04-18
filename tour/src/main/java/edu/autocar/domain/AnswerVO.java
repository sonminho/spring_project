package edu.autocar.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerVO {
	private Integer ano;
	private Integer rno;
	private String replytext;
	private String replyer;
	private Date regDate;
	private Date updateDate;
}
