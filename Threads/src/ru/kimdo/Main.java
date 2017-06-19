package ru.kimdo;

public class Main {


    public static void main(String[] args) {
	// write your code here
    }

}
class MyClass {
    private final int size = 10000000;
    private final int h = size/2;
    private float arr[] = new float[size];

    void simpleCount(){
        for (float e :
                arr) {
            e = 1;
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i/5)
                                    * Math.cos(0.2f + i/5)
                                    * Math.cos(0.4f + i/2));
        }
        System.out.println(System.currentTimeMillis() - time);
    }
    void treadCount(){
        for (float e :
                arr) {
            e = 1;
        }
        final float a1[] = new float[h];
        float a2[] = new float[h];
        long time = System.currentTimeMillis();
        System.arraycopy(arr,0,a1,0,h);
        System.arraycopy(arr,h,a2,0,h);
        TestThreadClass forTread = new TestThreadClass(a1);
        Thread tread_1 = new Thread(forTread);
        tread_1.start();
        for (int i = h; i < size; i++){
            a2[i] = (float)(a2[i]   * Math.sin(0.2f + i/5)
                                    * Math.cos(0.2f + i/5)
                                    * Math.cos(0.4f + i/2));
        }
        System.arraycopy(a1,0,arr,0,h);
        System.arraycopy(a2,0,arr,h,size);
        System.out.println(System.currentTimeMillis() - time);
    }
    class TestThreadClass implements Runnable {
        private float a[];

        TestThreadClass(float a[]){
            this.a = new float[a.length];
            this.a = a;
        }
        @Override
        public void run(){
            for (int i = 0; i < h; i++){
                a[i] = (float)(a[i] * Math.sin(0.2f + i/5)
                                    * Math.cos(0.2f + i/5)
                                    * Math.cos(0.4f + i/2));
            }
        }
        float[] getA(){
            return this.a;
        }
    }
}
