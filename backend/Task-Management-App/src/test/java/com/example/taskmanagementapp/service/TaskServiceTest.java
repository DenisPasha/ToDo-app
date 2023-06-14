package com.example.taskmanagementapp.service;
import com.example.taskmanagementapp.model.dto.TaskDto;
import com.example.taskmanagementapp.model.entities.Task;
import com.example.taskmanagementapp.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    private  TaskRepository taskRepository;
    private  ModelMapper modelMapper;
    private  PDFExporter pdfExporter;
    private TaskService taskService;


    @BeforeEach
    public void setup(){
        taskRepository = Mockito.mock(TaskRepository.class);
        pdfExporter = Mockito.mock(PDFExporter.class);
        modelMapper = new ModelMapper();
        taskService = new TaskService(taskRepository , modelMapper , pdfExporter);

    }

    public List<Task> createListOfTasksForTesting(){
        Task firstTask = new Task();
        firstTask.setTitle("A Title");
        firstTask.setDescription("desc");

        Task secondTask = new Task();
        secondTask.setTitle("Title 2");
        secondTask.setDescription("desc 2");
        List<Task> list = new ArrayList<>();
        list.add(firstTask);
        list.add(secondTask);
        return list;
    }
    public TaskDto createTasksDtoForTesting(){
        return new TaskDto("Test Title", "Test Description");
    }

    @Test
    public void testCreateTask() {
        // Assure
        TaskDto dto = createTasksDtoForTesting();
        Task map = modelMapper.map(dto, Task.class);


        when(taskRepository.save(any(Task.class))).thenReturn(map);
        taskService.createTask(dto);
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);

        verify(taskRepository).save(captor.capture());
        assertEquals(captor.getValue().getTitle() , dto.getTitle());
        assertEquals(captor.getValue().getDescription() , dto.getDescription());

    }

    @Test
    public void testCheckIfTaskWithTitleExist(){
        TaskDto dto = createTasksDtoForTesting();
        Task map = modelMapper.map(dto, Task.class);

        when(taskRepository.findTaskByTitle(any(String.class)))
                .thenReturn(Optional.of(map));

        boolean testTitle =
                taskService.checkIfTaskWithTitleExist("Test Title");

        assertTrue(testTitle);
    }

    @Test
    public void testEditTask(){
        TaskDto dto = createTasksDtoForTesting();
        Task map = modelMapper.map(dto, Task.class);

        when(taskRepository.findTaskByTitle(any(String.class)))
                .thenReturn(Optional.of(map));

        taskService.editTask("Test Title" , "new description");

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());
        Task capture = captor.getValue();

        assertEquals(map.getTitle() , capture.getTitle());
        assertEquals("new description" , capture.getDescription());

    }

    @Test
    public void testGetAllTasks(){
        List<Task> listOfTasksForTesting = createListOfTasksForTesting();
        Task firstTask = listOfTasksForTesting.get(0);
        Task secondTask = listOfTasksForTesting.get(1);


        when(taskRepository.findAll()).thenReturn(List.of(firstTask , secondTask));
        List<TaskDto> allTasks = taskService.getAllTasks();

        assertEquals(2 , allTasks.size());
        assertEquals(firstTask.getTitle() , allTasks.get(0).getTitle());
    }

    @Test
    public void testDeleteTask(){
        List<Task> listOfTasksForTesting = createListOfTasksForTesting();
        Task firstTask = listOfTasksForTesting.get(0);
        Task secondTask = listOfTasksForTesting.get(1);

        when(taskRepository.findTaskByTitle("A title")).thenReturn(Optional.of(firstTask));
        taskService.deleteTask("A title");

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).delete(captor.capture());
        Task value = captor.getValue();

        assertEquals(firstTask.getTitle() , value.getTitle());
    }



}