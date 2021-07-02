# JavaMultithreadingProgram

- Used Mutex, Semaphore to make sure each thread is running in order, when one thread is using the resource, some threads will wait until the resource is available.

- this program is to simuate the following senario
- A SCHOOL DAY DURING COVID
Students follow a hybrid school schedule. On the in-face learning day students arrive at school 
(simulated by sleep of random time) and wait in the schoolyard to be allowed to enter the school. 
Once the school day starts (after most students arrive) the principal will call each student. If a 
student is late for school she/he will terminate as well.
The next step is for the principal to decide if the student will be tested for COVID or not. From
each student a random number will be generated and 1 in 3 students will be tested. First students 
wait to see if they will be picked for testing. Students that are picked to be tested for COVID will 
all move on to the nurse’s room and will wait for the nurse to arrive and give the test. The 
probability of being tested positive is 3%. If the student tested positive, he/she will be sent home 
(the thread will terminate) else he/she will move on to the classroom.
Once all students that needed to be tested have been tested the nurse will leave (terminate).
Students will move on to the classrooms and wait for their teacher to arrive. The school day has 
2 sessions and students will attempt to attend each of them: ELA and MATH. There is a 
classroom for the ELA class, and a classroom for the MATH class. Each classroom has the 
capacity of studentsCap (default 6). Students will attempt to take the ELA class first and if space, 
they will wait for the teacher to arrive in the class. If the classroom is full (if you use shared 
variables make sure you implement mutual exclusion over their access), they will attempt to take 
the MATH class and if that is also full they will go in the backyard and play until the second session 
starts.
There are two teachers one for ELA, and one for MATH. Once the teacher arrives (s)he will let 
students in. (S)He will get ready, while students will wait for the class to start and eventually end.
The principal will keep track of how long the sessions are (by sleeping for a fixed amount of time).
Once the session ends, the principal will signal the teachers. The teachers will signal the students 
of their class.
After the two sessions have been completed everybody is ready to go home (terminate). At the 
level of each class, students will leave in the order of their id/name. The last to leave within the 
class will be the teacher (this must be done using semaphores). 
For example: if the last MATH class was attended by student5, student9 and student3 then 
student3 would leave first, followed by student5, followed by students9 and then followed by the 
MATH teacher
Before leaving each student should display in the order of attendance the session that they 
attended.
Once all students and teachers leave, the principal will leave too (use semaphores)
