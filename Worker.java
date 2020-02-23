// This class implements runnable interface, which will
// be helpful in running filtering concurrently on multiple
// java threads
class Worker implements Runnable {
    Filter filter; // being an interface, which is implemented by all
    // filters, we're generalizing whole thing
    String target; // filtered image path
    int order; // which order filter to be applied

    Worker(Filter filter, String target, int order) {
        this.filter = filter;
        this.target = target;
        this.order = order;
    }

    public void run() {
        System.out.println("[*] Applying Order " + order + " " + filter.filterName() + " ( "
                + Thread.currentThread().getName() + " )");
        filter.filterAndSave(target, order);
        System.out.println("[+] Success ( " + Thread.currentThread().getName() + " )");
    }
}