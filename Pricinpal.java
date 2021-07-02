class Principal extends Thread
{

    Principal(){
        setName("Principle ");
    }

    public void run()
    {
        try {
            Thread.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        msg("call each student");                       //start the attendance



        // finish check in, let the student enter the school
        Main.checkIn.release();



        //wait for class to start
        try {
            Main.classStarted.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //signal the class ended
        msg("Section 1 ended");
        Main.classEnded.release();

        //start section 2
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Main.section2.release();
        msg("Section 2 Started");




        //wait for class 2 to start
        try {
            Main.class2Started.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //signal the class 2 ended
        msg("Section 2 ended");
        Main.class2Ended.release();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //signal class to leave in order
        Main.tELeave.release();

    }



    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+getName()+": "+m);
    }
}