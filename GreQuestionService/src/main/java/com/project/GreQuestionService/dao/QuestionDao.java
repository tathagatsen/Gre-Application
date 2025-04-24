package com.project.GreQuestionService.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.GreQuestionService.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
	
//	@Query(value="SELECT * FROM Question q where q.quiz_id =: id ",nativeQuery=true)
	public List<Question> findByQuizId(Integer id);

}
