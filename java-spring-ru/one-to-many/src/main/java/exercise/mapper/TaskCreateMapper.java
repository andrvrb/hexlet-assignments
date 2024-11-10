package exercise.mapper;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Task;
import exercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskCreateMapper implements Mapper<TaskCreateDTO, Task> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task map(TaskCreateDTO object) {
        Task task = new Task();
        task.setTitle(object.getTitle());
        task.setDescription(object.getDescription());
        var assigneeId = object.getAssigneeId();
        var assigneeUser = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + assigneeId));
        task.setAssignee(assigneeUser);
        return task;
    }


}
