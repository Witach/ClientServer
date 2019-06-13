package sample.ServerConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TaskController {
        private ExecutorService executorForHeavTasks;
        private ExecutorService executorForLigtTask;

        public TaskController(int numberOfLightThreads){
            this.executorForHeavTasks = Executors.newCachedThreadPool();
            this.executorForLigtTask = Executors.newFixedThreadPool(numberOfLightThreads);
        }

        synchronized public void addTask(Task task){
            if(task.paramteres[0].equals("SEND"))
                executorForHeavTasks.submit(task);
            else
                executorForLigtTask.submit(task);
        }
}
