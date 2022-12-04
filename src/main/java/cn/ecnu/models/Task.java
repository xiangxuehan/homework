package cn.ecnu.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private int sequence;
    private String name;
    private int executeTime;
    private int releaseTime;
    private List<Task> nextTaskList = new ArrayList<>();
    private int priority;
    private boolean isExecuting;
    private List<Task> predecessors = new ArrayList<>();

    public Task(int sequence, String name, int executeTime, int releaseTime) {
        this.sequence = sequence;
        this.name = name;
        this.executeTime = executeTime;
        this.releaseTime = releaseTime;
    }

    public void addNextTask(Task task) {
        nextTaskList.add(task);
        task.predecessors.add(this);
    }

    public synchronized int getPriority() {
        this.priority = this.executeTime + this.nextTaskList.size() + this.getMaxPriorityOfNextTasks();
        return this.priority;
    }

    private int getMaxPriorityOfNextTasks() {
        int maxPriorityOfDependencies = 0;
        for (Task task : nextTaskList) {
            if (task.priority > maxPriorityOfDependencies) {
                maxPriorityOfDependencies = task.getPriority();
            }
        }
        return maxPriorityOfDependencies;
    }

}
