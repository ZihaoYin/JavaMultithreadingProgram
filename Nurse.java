class Nurse extends Thread
{
    Nurse(int id){
        setName("Nurse "+ id);
    }


    public void run()
    {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msg("Nurse arrived");

        Main.nurseArrive.release();


            //wait until testing finished
        for(int i=0;i<Main.testingStudentCount;i++){
            try {
                Main.nurseLeave.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        msg("Nurse left");

    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+getName()+": "+m);
    }
}