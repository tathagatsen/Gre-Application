package com.project.GreApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends JpaRepository<Word, Integer>{

	boolean existsByWord(String word);
}
