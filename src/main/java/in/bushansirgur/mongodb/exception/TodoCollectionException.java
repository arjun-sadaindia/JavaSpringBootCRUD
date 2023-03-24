package in.bushansirgur.mongodb.exception;

public class TodoCollectionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TodoCollectionException(String message){
		super(message);
	}
	
	public static String NotFoundException(String id) {
		return " todo with " + id + " not found! ";
	}
	
	public static String TodoAlreadyExists() {
		return "todo with given name already exists";
	}

}
