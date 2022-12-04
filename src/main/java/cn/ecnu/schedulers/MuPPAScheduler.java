package cn.ecnu.schedulers;

import cn.ecnu.models.CPUModel;
import cn.ecnu.models.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MuPPAScheduler {
    private static List<Task> taskList = new ArrayList<>();
    private static CPUModel p1 = new CPUModel("p1");
    private static CPUModel p2 = new CPUModel("p2");
    private static CPUModel p3 = new CPUModel("p3");


    static {
        Task task1 = new Task(1, "j1", 1, 0);
        taskList.add(task1);
        Task task2 = new Task(2, "j2", 2, 0);
        taskList.add(task2);
        Task task3 = new Task(3, "j3", 1, 0);
        taskList.add(task3);
        Task task4 = new Task(4, "j4", 4, 4);
        taskList.add(task4);
        Task task5 = new Task(5, "j5", 1, 0);
        taskList.add(task5);
        Task task6 = new Task(6, "j6", 3, 0);
        taskList.add(task6);
        Task task7 = new Task(7, "j7", 1, 0);
        taskList.add(task7);
        Task task8 = new Task(8, "j8", 1, 6);
        taskList.add(task8);
        Task task9 = new Task(9, "j9", 1, 0);
        taskList.add(task9);
        Task task10 = new Task(10, "j10", 2, 0);
        taskList.add(task10);
        Task task11 = new Task(11, "j11", 2, 0);
        taskList.add(task11);
        task1.addNextTask(task4);
        task2.addNextTask(task4);
        task2.addNextTask(task5);
        task3.addNextTask(task5);
        task4.addNextTask(task6);
        task4.addNextTask(task7);
        task5.addNextTask(task8);
        task5.addNextTask(task9);
        task6.addNextTask(task10);
        task7.addNextTask(task10);
        task8.addNextTask(task11);
        task9.addNextTask(task11);
    }



    public static void main(String[] args) {
        for (Task task : taskList) {
            int priority = task.getPriority();
            System.out.println("priority of " + task.getName() + " is " + priority);
        }
        Collections.sort(taskList, (o1, o2) -> {
            if (o1.getPriority() != o2.getPriority()) {
                return o2.getPriority() - o1.getPriority();
            } else if (o1.getReleaseTime() != o2.getReleaseTime()) {
                return o1.getReleaseTime() - o2.getReleaseTime();
            } else if (o1.getExecuteTime() != o2.getExecuteTime()) {
                return o2.getExecuteTime() - o1.getExecuteTime();
            } else {
                return o1.getSequence() - o2.getSequence();
            }
        });

        int totalTime = 0;
        while (!taskList.isEmpty()) {
            for (Task task : taskList) {
                if (task.getPredecessors().isEmpty() && task.getReleaseTime() <= totalTime && !task.isExecuting()) {
                    if (p1.isFree()) {
                        task.setExecuting(true);
                        p1.executeTask(task);
                    } else if (p2.isFree()) {
                        task.setExecuting(true);
                        p2.executeTask(task);
                    } else if (p3.isFree()) {
                        task.setExecuting(true);
                        p3.executeTask(task);
                    } else {
                        break;
                    }
                }
            }
            p1.passTime(1);
            p2.passTime(1);
            p3.passTime(1);
            totalTime++;
            if (p1.isFree()) {
                Task lastCompletedTaskOfP1 = p1.getHistoricalTasks().get(p1.getHistoricalTasks().size() - 1);
                if (lastCompletedTaskOfP1.isExecuting()) {
                    taskCompleted(lastCompletedTaskOfP1);
                }
            }
            if (p2.isFree()) {
                Task lastCompletedTaskOfP2 = p2.getHistoricalTasks().get(p2.getHistoricalTasks().size() - 1);
                if (lastCompletedTaskOfP2.isExecuting()) {
                    taskCompleted(lastCompletedTaskOfP2);
                }
            }
            if (p3.isFree()) {
                Task lastCompletedTaskOfP3 = p3.getHistoricalTasks().get(p3.getHistoricalTasks().size() - 1);
                if (lastCompletedTaskOfP3.isExecuting()) {
                    taskCompleted(lastCompletedTaskOfP3);
                }
            }
        }
        System.out.println("totalTime is " + totalTime);
        System.out.println("p1 executeTime is " + p1.getTotalExecuteTime());
        System.out.println("p1 task include " + p1.getHistoricalTasks().stream().map(task -> task.getName()).collect(Collectors.joining(",")));
        System.out.println("p2 executeTime is " + p2.getTotalExecuteTime());
        System.out.println("p2 task include " + p2.getHistoricalTasks().stream().map(task -> task.getName()).collect(Collectors.joining(",")));
        System.out.println("p3 executeTime is " + p3.getTotalExecuteTime());
        System.out.println("p3 task include " + p3.getHistoricalTasks().stream().map(task -> task.getName()).collect(Collectors.joining(",")));

    }

    private static void taskCompleted(Task lastCompletedTask) {
        taskList.remove(lastCompletedTask);
        for (Task task : taskList) {
            task.getPredecessors().remove(lastCompletedTask);
        }
        lastCompletedTask.setExecuting(false);
    }
}
