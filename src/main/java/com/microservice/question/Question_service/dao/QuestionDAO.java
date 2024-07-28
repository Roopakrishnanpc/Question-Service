package com.microservice.question.Question_service.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservice.question.Question_service.Entity.Question;

@Repository
public interface QuestionDAO extends CrudRepository<Question, Integer>{

	Iterable<Question> findByCategory(String category);

    @Modifying
    @Query("DELETE FROM Question q WHERE q.category = :category")
    int deleteAllByCategory(String category);
    @Query(value="Select q.id FROM Question q WHERE q.category = :category Order by RAND() limit :noOfQuestions", nativeQuery=true)
	Iterable<Integer> findRandomQuestionByCategory(String category, int noOfQuestions);
    
  //  @Query(value="SELECT q.id,q.question_title, q.choice1, q.choice2, q.choice3, q.choice4 FROM Question q WHERE q.id IN :questionID", nativeQuery=true)
    @Query(value="SELECT * FROM Question q WHERE q.id IN :questionID", nativeQuery=true)
    Iterable<Question> findQuestionsByQuestionIDs(@Param("questionID") Iterable<Integer> questionID);
 //   @Query(value="SELECT q.question_title, q.choice1, q.choice2, q.choice3, q.choice4 FROM Question q WHERE q.id = :questionID", nativeQuery=true)
  //  Question findQuestionsByQuestionIDs(@Param("questionID") Integer questionID);
}
