package de.demo.emf.component;

import java.io.IOException;

import org.demo.todolist.Item;
import org.demo.todolist.ToDoListFactory;
import org.demo.todolist.ToDoListPackage;
import org.demo.todolist.TodoList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osgi.service.component.annotations.*;

@Component
public class Example {

	@Reference
	ToDoListPackage todoPackage;
	
	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED, target = "(emf.model.name=todolist)")
	ResourceSet set;
	
	@Activate
	public void activate() {
		ToDoListFactory factory = todoPackage.getToDoListFactory();
		TodoList todoList = factory.createTodoList();
		todoList.setId("1");
		todoList.setName("Hello World TodoList");
		
		Item item = factory.createItem();
		item.setName("Todo");
		item.setDetails("Details");
		item.setId("item 1");
		
		todoList.getItems().add(item);
		
		Resource resource = set.createResource(URI.createURI("file://todo.xmi"), "application/octetstream");
		resource.getContents().add(todoList);
		
		try {
			resource.save(System.out, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
