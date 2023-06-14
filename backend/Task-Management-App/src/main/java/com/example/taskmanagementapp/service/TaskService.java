package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.model.dto.TaskDto;
import com.example.taskmanagementapp.model.entities.Task;
import com.example.taskmanagementapp.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private static final String outputPath = "src/main/java/com/example/taskmanagementapp/output/file.pdf";
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final PDFExporter pdfExporter;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper, PDFExporter pdfExporter) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.pdfExporter = pdfExporter;
    }


    public Boolean createTask(TaskDto taskDto) {
        Task taskEntity = modelMapper.map(taskDto, Task.class);

        boolean taskWithTitleAlreadyExist = checkIfTaskWithTitleExist(taskDto.getTitle());

        if (!taskWithTitleAlreadyExist){
            Task save = this.taskRepository.save(taskEntity);
           return true;
        }else {
            return false;
        }

    }

    public boolean checkIfTaskWithTitleExist(String titleOfTaskToEdit) {
        Task task = this.taskRepository.findTaskByTitle(titleOfTaskToEdit).orElse(null);
       return task != null;
    }

    public Boolean editTask(String title , String newDescription) {
        Task task = this.taskRepository.findTaskByTitle(title).orElse(null);

        boolean isTaskExist =
                checkIfTaskWithTitleExist(title);

        if (isTaskExist){
            task.setDescription(newDescription);
            this.taskRepository.save(task);
        }

        return isTaskExist;


    }

    public List<TaskDto> getAllTasks() {
        List<Task> all = this.taskRepository.findAll();
        List<TaskDto> tasksToDisplay = new ArrayList<>();

        for (Task task : all) {
            TaskDto map = modelMapper.map(task, TaskDto.class);
            tasksToDisplay.add(map);
        }
       return tasksToDisplay;
    }

    public boolean deleteTask(String titleToDelete) {

        Task taskToDelete = this.taskRepository.findTaskByTitle(titleToDelete).orElse(null);
        if (taskToDelete!=null){
            this.taskRepository.delete(taskToDelete);
            return true;
        }else {
            return false;
        }

    }

    public Boolean exportToPDF() throws IOException {
        List<TaskDto> allTasks = getAllTasks();
        boolean hasTasksToExport = allTasks.size() > 0;


        if (hasTasksToExport){
            this.pdfExporter.exportToPDF(allTasks , outputPath);
            return true;
        }
        return false;

    }
}
