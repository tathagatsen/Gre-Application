package com.project.GreApp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.GreApp.model.Word;

@Repository
public interface AppDao extends JpaRepository<Word, Integer>{

	public boolean existsByWord(String word);

	public Optional<Word> findByWord(String word);
	
	@Query(value = "SELECT w.id FROM Word w ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
	public List<Integer> findRandomQuestionsById(Integer numQ);
	
}
