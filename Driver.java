class Driver {
    public static void main(String[] args) {
        Thread[] thrdArr = new Thread[6];
        int idx = 0;
        for (int i = 1; i < 3; i++) {
            thrdArr[idx] = new Thread(new Worker(new MeanFilter("sample.jpg"), "order_" + i + "_MeanFiltered.jpg", i),
                    "Thread_" + (idx + 1));
            idx++;
        }
        for (int i = 1; i < 3; i++) {
            thrdArr[idx] = new Thread(
                    new Worker(new MedianFilter("sample.jpg"), "order_" + i + "_MedianFiltered.jpg", i),
                    "Thread_" + (idx + 1));
            idx++;
        }
        for (int i = 1; i < 3; i++) {
            thrdArr[idx] = new Thread(new Worker(new ModeFilter("sample.jpg"), "order_" + i + "_ModeFiltered.jpg", i),
                    "Thread_" + (idx + 1));
            idx++;
        }
        for (Thread thread : thrdArr) {
            thread.start();
        }
        for (Thread thread : thrdArr) {
            try {
                thread.join(0);
            } catch (InterruptedException ie) {
                System.err.println(ie);
            }
        }
        System.out.println("[+]Done");
    }
}