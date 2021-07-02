import java.util.Random;

class Student extends Thread
{


    boolean goHome = false;
    int id;
    int arriveTime;
    boolean tookMath = false;
    boolean tookELA = false;

    Student(int i){
        setName("Student "+ i);
        id = i;
    }

    public void run()
    {

        Random r = new Random();
        int low = 1;
        int high = 100;
        arriveTime = r.nextInt(high-low) + low;

        try {Thread.sleep(arriveTime); } catch(Exception e) {
            e.printStackTrace();
        }

        msg("arrived");



        //busy wait until the principle checked all students
        try {
            Main.checkIn.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.checkIn.release();



        if(arriveTime >= 90){                       // terminate if late
            msg("go home because of late");
        }else{
            low = 1;
            high = 3;
            int covidRand = r.nextInt(high-low) + low;
            if(covidRand == 1){                         //test for covid
                Main.testingStudentCount++;
                                          // busy wait until nurse arrives
                try {
                    Main.nurseArrive.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.nurseArrive.release();

                msg("get tested");

                low = 1;
                high = 100;
                int testPositiveRand = r.nextInt(high-low) + low;
                if(testPositiveRand<=3){                   //test positive
                    msg("test result positive");
                    msg("go home because of covid");
                    goHome = true;
                }

                Main.nurseLeave.release();

            }

            if(!goHome){       // student who didn't go home

                Main.studentsAttendedClass.add(id);

                if(Main.ELAStudentCount<Main.studentsCap &&!tookELA) {
                    Main.ELAStudentCount++;
                    tookELA = true;

                    //wait for teachers to arrive
                    try {
                        Main.ELATeacherArrived.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.ELATeacherArrived.release();

                    //wait for teachers to start the lecture
                    try {
                        Main.ELALectureStarted.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.ELALectureStarted.release();


                    //wait for teachers to end the lecture
                    try {
                        Main.ELAClassEnded.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.ELAClassEnded.release();

                    msg(" section 1 ELA class ended");


                }else if(Main.mathStudentCount<Main.studentsCap && !tookMath){

                    Main.mathStudentCount++;
                    tookMath = true;

                    //wait for teachers to arrive
                    try {
                        Main.mathTeacherArrived.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.mathTeacherArrived.release();

                    //wait for teachers to start the lecture
                    try {
                        Main.mathLectureStarted.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.mathLectureStarted.release();

                    //wait for teachers to end the lecture
                    try {
                        Main.mathClassEnded.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.mathClassEnded.release();

                    msg(" section 1 Math class ended");
                }else{ // go to play at backyard

                }




                try {
                    Main.section2.acquire();   //section 2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Main.section2.release();


                if(Main.ELAStudentCount<Main.studentsCap &&!tookELA) {
                    Main.ELAStudentCount++;
                    tookELA = true;
                    Main.s2Attendence.add(id);
                    Main.s2ELA.add(id);


                    //wait for teachers to start the lecture
                    try {
                        Main.ELALecture2Started.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.ELALecture2Started.release();


                    //wait for teachers to end the lecture
                    try {
                        Main.ELAClass2Ended.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.ELAClass2Ended.release();

                    msg(" section 2 ELA class ended");


                }else if(Main.mathStudentCount<Main.studentsCap && !tookMath){

                    Main.mathStudentCount++;
                    tookMath = true;
                    Main.s2Attendence.add(id);
                    Main.s2Math.add(id);


                    //wait for teachers to start the lecture
                    try {
                        Main.mathLecture2Started.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.mathLecture2Started.release();

                    //wait for teachers to end the lecture
                    try {
                        Main.mathClass2Ended.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Main.mathClass2Ended.release();

                    msg(" section 2 Math class ended");
                }else{ // go to play at backyard
                    Main.s2Attendence.add(id);
                    Main.s2PE.add(id);
                }


            }



        }




    }


    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+getName()+": "+m);
    }


}