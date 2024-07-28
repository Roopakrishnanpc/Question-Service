package com.microservice.question.Question_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservice.question.Question_service.Entity.Question;
import com.microservice.question.Question_service.Wrapper.QuestionWrapper;
import com.microservice.question.Question_service.Wrapper.ValidationResponse;
import com.microservice.question.Question_service.dao.QuestionDAO;


import jakarta.transaction.Transactional;

@Service
public class QuestionService {
	@Autowired
	QuestionDAO questionDAO;
	public ResponseEntity<Iterable<Question>>  getAllQuestion() {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<>( questionDAO.findAll(),HttpStatus.OK);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>( new ArrayList(),HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<Iterable<Question>> getQuestionsByCategory(String category) {
		// TODO Auto-generated method stub
		try
		{
		return new ResponseEntity( questionDAO.findByCategory(category),HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>( new ArrayList(),HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<String> addQuestion(Question question) {
		// TODO Auto-generated method stub
		try
		{
		questionDAO.save(question);
		int id= question.getId();
		return new ResponseEntity("Hola, data added successfully with id: " +id, HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>("Sorry, request not accepted",HttpStatus.BAD_REQUEST);
	}
	public String deleteQuestions() {
		// TODO Auto-generated method stub
		questionDAO.deleteAll();
		return "Deleted sucessfully";
	}
    @Transactional
    public ResponseEntity<String> deleteQuestionsByCategory(String category) {
        try {
            int deletedCount = questionDAO.deleteAllByCategory(category);
            if (deletedCount > 0) {
                 return ResponseEntity.ok("Successfully deleted " + deletedCount + " questions with category: " + category);
            } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No questions found for the category: " + category);
            }
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while deleting questions with category: " + category);
        }
    }
//	public boolean updateQuestionsByCategory(String category, Question question) {
//		// TODO Auto-generated method stub
//		Question realQuestion=new Question();
//		if(questionDAO.findByCategory(category)==null)
//		{
//			return false;
//		}
//		else
//		{
//			realQuestion.setCategory(question.getCategory());
//			realQuestion.setChoice1(question.getChoice1());
//			realQuestion.setChoice2(question.getChoice2());
//			realQuestion.setChoice3(question.getChoice3());
//			realQuestion.setChoice4(question.getChoice4());
//			realQuestion.setDifficultyLevel(question.getDifficultyLevel());
//			realQuestion.setQuestionTitle(question.getQuestionTitle());
//			realQuestion.setRightAnswer(question.getRightAnswer());
//			questionDAO.save(question);
//			return true;
//		}
		
//	}
	public boolean updateQuestionsByCategory(String category, Question updatedQuestion) {
	    // Fetch the existing question by category
	    Iterable<Question> existingQuestions =  questionDAO.findByCategory(category);
	    
	    if (existingQuestions == null || !existingQuestions.iterator().hasNext()) {
	        return false; // No question found for the given category
	    }
	    for (Question existingQuestion : existingQuestions) { 
	    // Update the existing question with the new values
	    existingQuestion.setCategory(updatedQuestion.getCategory());
	    existingQuestion.setChoice1(updatedQuestion.getChoice1());
	    existingQuestion.setChoice2(updatedQuestion.getChoice2());
	    existingQuestion.setChoice3(updatedQuestion.getChoice3());
	    existingQuestion.setChoice4(updatedQuestion.getChoice4());
	    existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
	    existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
	    existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
	    
	    // Save the updated question
	    questionDAO.save(existingQuestion);
	    }
	    return true;
	}

		public ResponseEntity<Iterable<Integer>> createQuestionsIDForQuiz(String category, Integer noOfQuestions) {
			// TODO Auto-generated method stub
			try {
		         
	            Iterable<Integer> questionsID = questionDAO.findRandomQuestionByCategory(category,noOfQuestions);

	            
	            if (!questionsID.iterator().hasNext()) {
	            	 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }


	            return new ResponseEntity<>(questionsID, HttpStatus.CREATED);
	        } catch (Exception e) {
	            // Log the error and return a 500 Internal Server Error status
	            e.printStackTrace();
	            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
	        }
			//return null;
		
	
	}
		public ResponseEntity<List<QuestionWrapper>> createQuestionsForQuiz(Iterable<Integer> questionID) {
			// TODO Auto-generated method stub
			//first creating arraylist to store questions
//			List<Question> questionsForEachId =new ArrayList<>();
//					//questionDAO.findQuestionsByQuestionIDs(questionID);
//			List<QuestionWrapper> questionsWrapper=new ArrayList<>();
//			for(Integer id: questionID)
//			{//now adding questions for id
//				//questionsWrapper.addAll(questionInUI);
//				((ArrayList) questionsForEachId).add(questionDAO.findById(id).get());
//			}
//			for(Question getQuestion:questionsForEachId)
//			{
//				QuestionWrapper questionUISpecific=new QuestionWrapper(getQuestion.getId(),getQuestion.getQuestionTitle(),
//						getQuestion.getChoice1(),getQuestion.getChoice2(), getQuestion.getChoice3(),
//						getQuestion.getChoice4());
//				questionsWrapper.add(questionUISpecific);
//			}
			//anotherwayof implementation
//			for(Question getQuestion:questionsForEachId)
//			{
//				QuestionWrapper questionUISpecific=new QuestionWrapper();
//				questionUISpecific.setId(getQuestion.getId());
//				questionUISpecific.setQuestionTitle(getQuestion.getQuestionTitle());
//				questionUISpecific.setChoice1(getQuestion.getChoice1());
//				questionUISpecific.setChoice2(getQuestion.getChoice2());
//				questionUISpecific.setChoice3(getQuestion.getChoice3());
//				questionUISpecific.setChoice4(getQuestion.getChoice4());
//				questionsWrapper.add(questionUISpecific);
//			}
//			return new ResponseEntity<>(questionsWrapper, HttpStatus.OK);
//		}		//anotherwayfor above
            Iterable<Question> questions = questionDAO.findQuestionsByQuestionIDs(questionID);

            // Convert Iterable to List
            List<Question> questionList = new ArrayList<>();
            questions.forEach(questionList::add);

            // Convert Question entities to QuestionWrapper DTOs
            List<QuestionWrapper> questionWrappers = questionList.stream()
                .map(q -> new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getChoice1(), q.getChoice2(), q.getChoice3(), q.getChoice4()))
                .collect(Collectors.toList());

            if (questionWrappers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
            }

            return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
		}
	
	//interact quiz to question service
	
	//getquestios(questionid
	//getscore
		public ResponseEntity<Integer> getResult(List<ValidationResponse> response)
		{
			
			int score = 0;
			for(ValidationResponse res: response)
			{
					Question getAnswer=questionDAO.findById(res.getId()).get();
					if((res.getResult()).equals(getAnswer.getRightAnswer()))
					{
						score++;
					}
			}
			return new ResponseEntity<Integer>(score, HttpStatus.OK);
		}
//create updateResponse -> variables sucesss, status, updateCount
	/*
	 * public UpdateResponse updateQuestionsByCategoryAnotherWay(String category,
	 * Question updatedQuestion) { // Fetch the existing questions by category
	 * Iterable<Question> existingQuestions = questionDAO.findByCategory(category);
	 * 
	 * if (existingQuestions == null || !existingQuestions.iterator().hasNext()) {
	 * return new UpdateResponse(false, "No questions found for the given category",
	 * 0); }
	 * 
	 * int updatedCount = 0;
	 * 
	 * // Iterate over each question and update it for (Question existingQuestion :
	 * existingQuestions) { // Update fields of the existing question with values
	 * from updatedQuestion
	 * existingQuestion.setChoice1(updatedQuestion.getChoice1());
	 * existingQuestion.setChoice2(updatedQuestion.getChoice2());
	 * existingQuestion.setChoice3(updatedQuestion.getChoice3());
	 * existingQuestion.setChoice4(updatedQuestion.getChoice4());
	 * existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
	 * existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
	 * existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
	 * 
	 * // Save the updated question questionDAO.save(existingQuestion);
	 * updatedCount++; }
	 * 
	 * return new UpdateResponse(true, "Successfully updated", updatedCount); }
	 */

}