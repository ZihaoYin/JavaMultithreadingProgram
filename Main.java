import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Main extends Thread{

    public static long time = System.currentTimeMillis();
    public static int nStudents = 20;
    public static volatile int testingStudentCount = 0;
    public static volatile int studentsCap = 6;
    public static volatile int mathStudentCount = 0;
    public static volatile int ELAStudentCount = 0;
    public static volatile int teacherArrivalCount = 0;
    public static volatile int classCount = 0;

    public static volatile Student[] students = new Student[nStudents];
    public static volatile Vector<Integer> studentsAttendedClass = new Vector<Integer>();
    public static volatile Vector<Integer>  s2Attendence = new Vector<Integer>();
    public static volatile Vector<Integer>  s2ELA = new Vector<Integer>();
    public static volatile Vector<Integer>  s2Math = new Vector<Integer>();
    public static volatile Vector<Integer>  s2PE = new Vector<Integer>();

    public static Semaphore checkIn = new Semaphore(0);     //make sure students wait until principal calls
    public static Semaphore nurseArrive = new Semaphore(0);  //make sure students wait until nurse arrives
    public static Semaphore section2 = new Semaphore(0);    //make sure everyone wait until section 2 starts
    public static Semaphore nurseLeave = new Semaphore(0);        // make sure nurse leave after students finished testing
    public static Semaphore ELATeacherArrived = new Semaphore(0);     //make sure students wait until ELA teacher arrives
    public static Semaphore mathTeacherArrived = new Semaphore(0);     //make sure students wait until math teacher arrives

    public static Semaphore classStarted = new Semaphore(0);            //let principal know classes started
    public static Semaphore ELALectureStarted = new Semaphore(0);       //make sure students wait until ELA teacher starts the class
    public static Semaphore mathLectureStarted = new Semaphore(0);      //make sure students wait until ELA teacher starts the class
    public static Semaphore classEnded = new Semaphore(0);              //let teachers know class ended
    public static Semaphore mathClassEnded = new Semaphore(0);          //let students know math class ended
    public static Semaphore ELAClassEnded = new Semaphore(0);           //let students know ELA class ended

    public static Semaphore class2Started = new Semaphore(0);           //same purpose like above but for section 2
    public static Semaphore ELALecture2Started = new Semaphore(0);
    public static Semaphore mathLecture2Started = new Semaphore(0);
    public static Semaphore class2Ended = new Semaphore(0);
    public static Semaphore mathClass2Ended = new Semaphore(0);
    public static Semaphore ELAClass2Ended = new Semaphore(0);

    public static Semaphore tELeave = new Semaphore(0);                 //let ELA class leave and then ELA teacher leave
    public static Semaphore tMLeave = new Semaphore(0);                  //let math class leave and then math teacher leave
    public static Semaphore pLeave = new Semaphore(0);                   //let backyard students leave and then principal leave
    public static Semaphore sLeave = new Semaphore(0);                   // let each student leave in order

    public static Semaphore mutex = new Semaphore(1);                   //prevent shared varible deadlock



    public static void main(String[] args) throws InterruptedException {


        //command line argument for the number of students
        if (args.length > 0) {
            nStudents = Integer.parseInt(args[0]);
        }


        for (int i = 0; i < nStudents;i++) {
            students[i] = new Student(i);
        }

        //run all the students threads
        for (int i = 0; i < nStudents; i++) {
            students[i].start();
        }

        //run principle
        Principal p = new Principal();
        p.start();

        //run nurse
        Nurse n = new Nurse(1);
        n.start();

        //run math teacher, ELA teacher thread
        Teacher tM = new Teacher("Math Teacher");
        Teacher tE = new Teacher("ELA Teacher");
        tM.start();
        tE.start();




        tELeave.acquire();
        sLeave.release();
        //print ELA attendances before leaving
        System.out.print("Section 2 ELA attendance: ");
        for(int i=0;i< s2ELA.size();i++){
            int j = s2ELA.get(i);
            System.out.print(" student "+students[j].id);
        }
        System.out.println();
        Collections.sort(s2ELA);

        //ELA class left in order
        for(int i=0;i< s2ELA.size();i++){

            int j = s2ELA.get(i);

            sLeave.acquire();
            students[j].msg("left");
            sLeave.release();
        }


        tE.msg("left");


        tMLeave.release();

        //print Math attendances before leaving
        System.out.print("Section 2 Math attendance: ");
        for(int i=0;i< s2Math.size();i++){
            int j = s2Math.get(i);
            System.out.print(" student "+students[j].id);
        }
        System.out.println();
        Collections.sort(s2Math);

        //Math class left in order
        for(int i=0;i<s2Math.size() ;i++){

            int j = s2Math.get(i);

            sLeave.acquire();
            students[j].msg("left");
            sLeave.release();
        }



        tMLeave.acquire();

        tM.msg("left");
        pLeave.release();

        //print backyard attendances before leaving
        System.out.print("Section 2 backyard attendance: ");

        for(int i=0;i<s2PE.size() ;i++){
            int j = s2PE.get(i);
            System.out.print(" student "+students[j].id);
        }
        System.out.println();
        Collections.sort(s2PE);

        //backyard left in order
        for(int i=0;i<s2PE.size() ;i++){
            int j = s2PE.get(i);
            sLeave.acquire();
            students[j].msg("left");
            sLeave.release();

        }



        pLeave.acquire();
        p.msg("left");

    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+getName()+": "+m);
    }
}






