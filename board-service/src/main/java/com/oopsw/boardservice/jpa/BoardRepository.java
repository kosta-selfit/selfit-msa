package com.oopsw.boardservice.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	Optional<BoardEntity> findTopByOrderByBoardIdDesc();

}
