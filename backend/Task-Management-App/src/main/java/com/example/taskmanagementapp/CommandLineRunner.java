package com.example.taskmanagementapp;

import com.example.taskmanagementapp.model.dto.TaskDto;
import com.example.taskmanagementapp.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public CommandLineRunner(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ======= Welcome to Task managing app =======");

        System.out.println("Please enter your command");
        System.out.println("Available commands are: ");
        System.out.println("Create");
        System.out.println("Edit");
        System.out.println("List All");
        System.out.println("Delete");
        System.out.println("Export");

        String command= "";
        if(scanner.hasNextLine()){
            command = scanner.nextLine();
        }

        while (!command.equals("End program")){

            switch (command){
                case "Create":
                    createTask(scanner);
                    break;
                case "Edit":
                    editTask(scanner);
                    break;
                case "List All":
                    listAllTaks();
                    break;
                case "Delete":
                    deleteTask(scanner);
                    break;
                case "Export":
                    exportToPDF();
                    break;
                default:
                    System.out.println("Please Enter valid command");
            }
            command = scanner.nextLine();
        }
    }

    private void exportToPDF() throws IOException {
        Boolean hasTaskToExport = this.taskService.exportToPDF();
        String toPrint =  hasTaskToExport ? "Tasks to export :" : "No tasks to export";
        System.out.println(toPrint);
    }

    private void deleteTask(Scanner scanner) {
        System.out.println("Please enter the title of the task to delete");
        String titleToDelete = scanner.nextLine();

        boolean isTaskDeleted = this.taskService.deleteTask(titleToDelete);

        System.out.println(isTaskDeleted ? "Successfully deleted task" : "Task  does not exist");
    }

    private void listAllTaks() {
        List<TaskDto> allTasks = this.taskService.getAllTasks();

        if (allTasks.size() == 0){
            System.out.println("You have no task , please create one ");
            return;
        }
        Collections.sort(allTasks , Comparator.comparing(TaskDto::getTitle));

        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder tasksBuilder = new StringBuilder();

        for (TaskDto taskDto : allTasks) {
            tasksBuilder.append(String.format("Title: %s" , taskDto.getTitle()))
                    .append(System.lineSeparator())
                    .append(String.format("Description: %s",taskDto.getDescription()))
                    .append(System.lineSeparator())
                    .append(String.format("==========================="))
                    .append(System.lineSeparator());
        }

        messageBuilder
                .append(String.format("Your Tasks: Ordered by Title Alphabetically"))
                .append(System.lineSeparator())
                .append(String.format("==================================="))
                .append(System.lineSeparator())
                .append(tasksBuilder.toString().trim());

        System.out.println(messageBuilder.toString().trim());
    }

    public void createTask(Scanner scanner){

        System.out.println("Please enter title for your new task");
        String title = scanner.nextLine();
        System.out.println("Please enter description for your new task");
        String description = scanner.nextLine();

        TaskDto dto = new TaskDto(title , description);
        Boolean isTaskCreated = this.taskService.createTask(dto);

        String print = "";

        print = isTaskCreated == true ? String.format("You have successfully added new task with title: ", title) :
                String.format("Task with title %s already exists" , title);

        System.out.println(print);
    }

    public void editTask(Scanner scanner){
        System.out.println("Please enter the title of the task you want to edit");
        String titleOfTaskToEdit = scanner.nextLine();
        System.out.println("Now please enter new description bellow");
        String newDescription = scanner.nextLine();

        boolean isTaskExist = this.taskService.editTask( titleOfTaskToEdit , newDescription);

       String print = isTaskExist == true ? String.format("Successfully changed description") :
                String.format("Task with title provided does not exist");

        System.out.println(print);
    }
}
