package sample.ServerConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
public class TaskController {
        LinkedBlockingQueue<Task> queueWithLightTask;
        LinkedBlockingQueue<Task> queueWithHeavyTask;
        List<Thread> listOfHeavyWorkers;
        List<Thread> listOfLightWorkers;
        public TaskController(LinkedBlockingQueue queueWithHeavyTask, LinkedBlockingQueue queueWithLightTask,
                              int amountOfHeavyWorkers, int amountOfLightWorkers){
            this.queueWithHeavyTask = queueWithHeavyTask;
            this.queueWithLightTask = queueWithLightTask;
            this.listOfHeavyWorkers = new ArrayList<>();
            this.listOfLightWorkers = new ArrayList<>();
            for(int i=0;i<amountOfHeavyWorkers;i++){
                listOfHeavyWorkers.add(new Thread(()->{
                    try {
                        while(true){
                            Task task = (Task)queueWithHeavyTask.poll();
                            task.strategy.reply(task.session,task.dirPath,task.paramteres);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                        System.exit(1);
                    }

                }));
            }
            for(int i=0;i<amountOfHeavyWorkers;i++){
                listOfHeavyWorkers.add(new Thread(()->{
                    try {
                        while(true){
                            Task task = (Task)queueWithLightTask.poll();
                            task.strategy.reply(task.session,task.dirPath,task.paramteres);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                        System.exit(1);
                    }

                }));
            }
        }

        public void addLightTask(Task task){
            try{
                queueWithLightTask.put(task);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        public void addHeavyTask(Task task){
            try{
                queueWithHeavyTask.put(task);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
}
