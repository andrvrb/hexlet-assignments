package exercise.controller;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.TaskCreateMapper;
import exercise.mapper.TaskMapper;
import exercise.mapper.TaskUpdateMapper;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskCreateMapper taskCreateMapper;

    @Autowired
    private TaskUpdateMapper taskUpdateMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<TaskDTO>> index() {
        var tasks = repository.findAll();
        var result = tasks.stream()
                .map(taskMapper::map)
                .toList();

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO create(@Valid @RequestBody TaskCreateDTO taskData) {
        var task = repository.save(taskCreateMapper.map(taskData));
        return taskMapper.map(task);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO show(@PathVariable Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        return taskMapper.map(task);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO update(@RequestBody @Valid TaskUpdateDTO taskData, @PathVariable Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        repository.save(taskUpdateMapper.map(taskData, task));
        return taskMapper.map(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
    // END
}
