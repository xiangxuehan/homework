package cn.ecnu.schedulers;

import cn.ecnu.models.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TaPSAScheduler {

    private static List<Task> taskList = new ArrayList<>();

//    static {
//        Task task1 = new Task(1, "task1", 1, 0);
//        Task task2 = new Task(2, "task2", 1, 3);
//        Task task3 = new Task(3, "task3", 2, 0);
//        Task task4 = new Task(4, "task4", 1, 0);
//        Task task5 = new Task(5, "task5", 1, 0);
//        task1.addNextTask(task4);
//        task1.addNextTask(task5);
//        task2.addNextTask(task4);
//        task2.addNextTask(task5);
//        task3.addNextTask(task5);
//        taskList.add(task1);
//        taskList.add(task2);
//        taskList.add(task3);
//        taskList.add(task4);
//        taskList.add(task5);
//    }

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

    /**
     * Input: 含有 n 个任务 J1，...，Jn 的有向无环图 G、G 的节点依赖关系矩阵、任务释放 时间表、任务执行时间表、任务优先级值表
     * Output: 任务优先级表
     * 第 1 步:按照优先级值从高到低进行排序，若没有优先级值相等，则排序过程结束， 输出排序表转入到第 3 步，否则转入第 2 步。
     * 第 2 步:将优先级值相等的任务进行组合，形成若干个子表。
     * 在每个子表中，先按 照释放时间先后顺序进行排序:释放时间早优先级高;
     * 若释放时间一致，则将按照执行 时间从大到小进行排序;
     * 若执行时间也相等则按照任务编号从小到大排序。
     * 将排序后各 个子表代替第 1 步按照优先级值排序表中相应子表，排序过程结束，输出排序表并转入 到第3步。
     * 第 3 步:算法结束。
     */
    public static void main(String[] args) {
        for(Task task :taskList){
            int priority = task.getPriority();
            System.out.println("priority of "+ task.getName()+" is "+priority);
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
        String result = taskList.stream().map(task -> task.getName())
                .collect(Collectors.joining(">"));
        System.out.println(result);
    }
}
