package exercise;

class SafetyList {
    // BEGIN
    private int[] mass = new int[0];
    private int size;

    public int getSize() {
        return size;
    }

    public int get(int index) {
        return mass[index];
    }

    public synchronized void add(int data) {
        int[] destArray = new int[mass.length + 1];
        for (int i = 0; i < mass.length; i++) {
            destArray[i] = mass[i];
        }
        destArray[destArray.length - 1] = data;
        mass = destArray;
        size++;
    }
    // END
}
