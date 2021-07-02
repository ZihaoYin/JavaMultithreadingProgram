class Teacher extends Thread
{

    String name;
    Teacher(String name){
        setName("Teacher "+ name);
        this.name = name;
    }


    public void run()
    {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msg(name+ " arrived");                         //teacher arrived
        if(name.equals("Math Teacher") ){
            Main.mathTeacherArrived.release();
            Main.teacherArrivalCount++;
        }else if(name.equals("ELA Teacher") ){
            Main.ELATeacherArrived.release();
            Main.teacherArrivalCount++;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





        msg(name + " lecture started");                //section 1 class started
        if (name.equals("Math Teacher")) {
            Main.mathLectureStarted.release();
        } else if (name.equals("ELA Teacher")) {
            Main.ELALectureStarted.release();
        }


        try {
            Main.mutex.acquire();                //mutex to prevent deadlock
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //class started signal for principle
        Main.classCount++;
        if(Main.classCount >=2){
            Main.classStarted.release();
        }

        Main.mutex.release();

        //wait class to end
        try {
            Main.classEnded.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.classEnded.release();


        //signal students
        msg("section 1 ended");
        if(name.equals("ELA Teacher")){
            Main.ELAClassEnded.release();
        }
        if(name.equals("Math Teacher")){
            Main.mathClassEnded.release();
        }



        if (name.equals("Math Teacher")) {

            Main.mathStudentCount=0;
        } else if (name.equals("ELA Teacher")) {


            Main.ELAStudentCount=0;
        }
        Main.classCount = 0;



        try {
            Main.section2.acquire();   //section 2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.section2.release();


        msg(name + " lecture started");                //section 2 class started
        if (name.equals("Math Teacher")) {
            Main.mathLecture2Started.release();
        } else if (name.equals("ELA Teacher")) {
            Main.ELALecture2Started.release();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Main.mutex.acquire();                //mutex to prevent deadlock
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.classCount++;
        if(Main.classCount >=2){
            Main.class2Started.release();           //class 2 started signal for principle
        }
        Main.mutex.release();

        //wait class to end
        try {
            Main.class2Ended.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.class2Ended.release();

        //signal students
        msg("section 2 ended");
        if(name.equals("ELA Teacher")){
            Main.ELAClass2Ended.release();
        }
        if(name.equals("Math Teacher")){
            Main.mathClass2Ended.release();
        }




    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+getName()+": "+m);
    }
}

