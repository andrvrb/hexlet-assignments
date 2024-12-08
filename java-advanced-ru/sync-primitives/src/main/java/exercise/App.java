package exercise;

class App {

    public static void main(String[] args) throws InterruptedException {
        // BEGIN
        SafetyList list = new SafetyList();
        ListThread thread1 = new ListThread(list);
        ListThread thread2 = new ListThread(list);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


        System.out.println("Size: " + list.getSize());
        // END
    }
}

