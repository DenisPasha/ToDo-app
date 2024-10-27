package com.example.taskmanagementapp.web;


import com.example.taskmanagementapp.model.dto.TaskDto;
import com.example.taskmanagementapp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class TaskController {


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list-of-tasks")
    public ResponseEntity<List<TaskDto>> getTasks(){
        List<TaskDto> allTasks = this.taskService.getAllTasks();

      return  allTasks.size() > 0 ? ResponseEntity.ok(allTasks) : ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/create-task",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<TaskDto> createTask (@RequestBody TaskDto dto){

        Boolean isTaskCreated = this.taskService.createTask(dto);
        if (isTaskCreated){
            return new ResponseEntity<>(dto , HttpStatus.CREATED);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/delete/{title}")
    public ResponseEntity<Boolean> delete (@PathVariable String title){

        boolean isDeleted = this.taskService.deleteTask(title);

        if (isDeleted){
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("edit/{title}")
    public ResponseEntity<TaskDto> editTask(@PathVariable String title , @RequestBody TaskDto dto){

        Boolean isTaskExist = taskService.editTask(title, dto.getDescription());

       return isTaskExist ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();

    }

    @GetMapping("export-to-pdf")
    public ResponseEntity<TaskDto> exportToPdf() throws IOException {

        Boolean hasTaskToExport = taskService.exportToPDF();

        return hasTaskToExport ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();

    }
}
