package org.kosa.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}

// TODO: 게시글 작성, 수정 시에 조회수 올림 X
// TODO: 검증에 실패한 곳에 focus
// TODO: 살짝 검색결과 없음 뜨는 거 없애기 가능?

// TODO: 트랜잭션 확인, OSIV?
// TODO: axios 인스턴스로 처리?
// TODO: CSRF 처리
