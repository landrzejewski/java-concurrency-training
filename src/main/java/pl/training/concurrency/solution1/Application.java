package pl.training.concurrency.solution1;

public class Application {

    public static void main( String args[] ) throws InterruptedException {
        Bathroom bathroom = new Bathroom();
        Thread female1 = asyncRun(() -> bathroom.femaleUseBathroom("Anna"));
        Thread male1 = asyncRun(() -> bathroom.maleUseBathroom("Jan"));
        Thread male2 = asyncRun(() -> bathroom.maleUseBathroom("Marek"));
        Thread male3 = asyncRun(() -> bathroom.maleUseBathroom("Adam"));
        Thread male4 = asyncRun(() -> bathroom.maleUseBathroom("Michał"));

        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();
        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();
    }

    interface Task {

        void run() throws InterruptedException;

    }

    private static Thread asyncRun(Task task) {
        return new Thread(() -> {
            try {
                task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
