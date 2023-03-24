package in.bushansirgur.mongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import in.bushansirgur.mongodb.model.TodoDTO;

@Repository

public interface TodoRepositiory extends MongoRepository<TodoDTO, String> {

	
	@Query("{'todo': ?0}")
	Optional<TodoDTO>findByTodo(String todo);
}
