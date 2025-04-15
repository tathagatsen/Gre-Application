package com.project.GreApp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends JpaRepository<Word, Integer>{

	public boolean existsByWord(String word);

	public Optional<Word> findByWord(String word);

//	public void findByWord(String word);
}
