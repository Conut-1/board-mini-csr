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
// TODO: invalid 시각화, 검증을 한 번에 띄우기

// TODO: 트랜잭션 확인, OSIV?
// TODO: Vue.js 빌드 설정

// TODO: DB에서 가져오는 값 줄이기
// TODO: axios 인스턴스로 처리?
// TODO: CSRF 처리
