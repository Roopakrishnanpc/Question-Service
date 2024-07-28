package com.microservice.question.Question_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.question.Question_service.Entity.Question;
import com.microservice.question.Question_service.Wrapper.QuestionWrapper;
import com.microservice.question.Question_service.Wrapper.ValidationResponse;
import com.microservice.question.Question_service.service.QuestionService;


@RestController
@RequestMapping("/question")
public class QuestonController {
	@Autowired
	QuestionService questionService;
	@Autowired
	Environment environement;
	@GetMapping("/allQuestions")
	//public Iterable<Question> getAllQuestion()
	public ResponseEntity<Iterable<Question>> getAllQuestion()
	{	
		return new ResponseEntity( questionService.getAllQuestion(),HttpStatus.OK);
		//return questionService.getAllQuestion();
	}
	@GetMapping("/category/{category}")
	//public Iterable<Question> getQuestionsByCategory(@PathVariable String category)
	public ResponseEntity<Iterable<Question>> getQuestionsByCategory(@PathVariable String category)
	{	
		return new ResponseEntity(questionService.getQuestionsByCategory(category), HttpStatus.OK);
	}
	@PostMapping("/addQuestions")
	//public String addQuestions(@RequestBody Question question)
	public ResponseEntity<String> addQuestions(@RequestBody Question question)
	{
		 return new ResponseEntity(questionService.addQuestion(question),HttpStatus.CREATED);
	}
	@DeleteMapping("/deleteQuestions")
	public String deleteQuestions()
	{
		 return questionService.deleteQuestions();
	}
    @DeleteMapping("/deleteQuestions/{category}")
    public ResponseEntity<String> deleteQuestionsByCategory(@PathVariable String category) {
        // Call the service method and get the response
        return questionService.deleteQuestionsByCategory(category);
    }
    
	
	@PutMapping("/updateQuestions/{category}")
	public String updateQuestionsByCategory(@PathVariable String category,@RequestBody Question question)
	{
		 boolean result= questionService.updateQuestionsByCategory(category, question);
		 if(result==true)
		 {
			 return "Sucessfully updated";
		 }
		 else
		 {
			 return "The mentioned category is not mentioned";
		 }
	}
	@GetMapping("/createID")//quiz/create?category=Java&noOfQuestions=5&title=HelloWorld
	public ResponseEntity<Iterable<Integer>> createQuestionsIDForQuiz(@RequestParam String category,@RequestParam Integer noOfQuestions)//,@RequestParam String title)
	{
		//to get to know the load balanace that which port is used which send request
			System.out.println(environement.getProperty("local.server.port"));
			return questionService.createQuestionsIDForQuiz(category,noOfQuestions);
	}
	@PostMapping("/createQuestions")
	//quiz/create?category=Java&noOfQuestions=5&title=HelloWorld
	public ResponseEntity<List<QuestionWrapper>> createQuestionsForQuiz(@RequestBody Iterable<Integer> questionId)//,@RequestParam String title)
	{
			return questionService.createQuestionsForQuiz(questionId);
	}
	@PostMapping("/getResult")
	public ResponseEntity<Integer> getResult(@RequestBody List<ValidationResponse> response)
	{
		ResponseEntity<Integer> result= questionService.getResult(response);
		return result;
	}
}
