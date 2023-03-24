package in.bushansirgur.mongodb.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.bushansirgur.mongodb.model.TodoDTO;
import in.bushansirgur.mongodb.repository.TodoRepositiory;
import jakarta.validation.ConstraintViolationException;
import in.bushansirgur.mongodb.exception.TodoCollectionException;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepositiory todoRepo;

	@Override
	public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
		Optional<TodoDTO>todoOptional = todoRepo.findByTodo(todo.getTodo());
		if (todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todo);
		}
		
		
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos = todoRepo.findAll();
		
		if (todos.size()>0) {
			return todos;
		}else {
			return new ArrayList<TodoDTO>();
		}
	}

	@Override
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
		Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
		if(!optionalTodo.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}else {
			return optionalTodo.get();
		}

	}

	@Override
	public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
		Optional<TodoDTO> todowithID =todoRepo.findById(id);
		Optional<TodoDTO> todowithSameName = todoRepo.findByTodo(todo.getTodo());
		if(todowithID.isPresent()) {
			
			if(todowithSameName.isPresent() && todowithSameName.get().getId().equals(id)) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
			}
			
			TodoDTO todoToUpdate = todowithID.get();
			todoToUpdate.setTodo(todo.getTodo());
			todoToUpdate.setDescription(todo.getDescription());
			todoToUpdate.setCompleted(todo.getCompleted());
			todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todoToUpdate);
			
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));		}
	}

	@Override
	public void deleteTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> todoOptional = todoRepo.findById(id);
		
		if(!todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}else {
			todoRepo.deleteById(id);
		}
	}



}
