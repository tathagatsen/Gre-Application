package com.project.GreApp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.GreApp.model.QuestionWrapper;
import com.project.GreApp.model.Word;

@Repository
public interface AppDao extends JpaRepository<Word, Integer>{

	public boolean existsByWord(String word);

	public Optional<Word> findByWord(String word);
	
	@Query(value = "SELECT * FROM Word w ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
	public List<Word> findRandomQuestionsById(Integer numQ);
	
	@Query(value="SELECT * FROM Word w where w.category = ?1 ",nativeQuery = true)
	public List<Word> findByCategory(String category);
	
	@Query(value="SELECT * FROM Word w where w.user_id=?1 ",nativeQuery = true)
	public List<Word> findAllByUserId(Integer userId);

	@Query(value="SELECT * FROM Word w WHERE w.user_id=?1  AND w.id = ?2  ",nativeQuery = true)
	public Optional<Word> findByIdAndWordId(Integer userId, Integer wordId);

	@Query(value = "SELECT * FROM Word w WHERE w.user_id=?1 AND w.word=?2",nativeQuery = true)
	public Optional<Word> findByWordAndUserId(Integer userId, String word);
	
}
