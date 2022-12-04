package cn.ecnu.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CPUModel {
    private String name;
    private int leftTime;
    private List<Task> historicalTasks = new ArrayList<>();

    public CPUModel(String name) {
        this.name = name;
    }

    public void executeTask(Task task) {
        historicalTasks.add(task);
        leftTime = task.getExecuteTime();
    }

    public void passTime(int time) {
        this.leftTime = (leftTime - time) > 0 ? leftTime - time : 0;
    }

    public boolean isFree() {
        return leftTime <= 0;
    }

    public int getTotalExecuteTime() {
        int sum = 0;
        for (Task task : historicalTasks) {
            sum += task.getExecuteTime();
        }
        return sum;
    }
}
