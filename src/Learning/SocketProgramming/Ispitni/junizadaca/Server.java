package Learning.SocketProgramming.Ispitni.junizadaca;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static int port = 5565;
    static List<object> subjects = new ArrayList<>();
    public static void main(String[] args) {
        subjects.add(new object(192011,2,100,1));
        subjects.add(new object(192032,2,55,1));
        subjects.add(new object(192007,2,78,1));
        subjects.add(new object(192021,2,90,1));
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s;
            ObjectInputStream ois;
            ObjectOutputStream oos;

            while(true){
                System.out.println("cekam na povrzuvanja");
                s = ss.accept();
                ois = new ObjectInputStream(s.getInputStream());
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.flush();
                var received = ois.readUTF();

                if(received.equals("Board")){
                    System.out.println("Adding new handler for board");
                    boardHandler bh = new boardHandler(oos,ois);
                    Thread t = new Thread(bh);
                    t.start();
                }
                if (received.equals("Teacher")){
                    System.out.println("Adding new handler for teacher " + s);
                    TeacherHandler th = new TeacherHandler(s,ois,oos);
                    Thread  t = new Thread(th);
                    t.start();
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TeacherHandler extends Thread{
    Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public TeacherHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos){
        this.s = s;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run(){
        System.out.println("Added teacher");
        while(true){
            try {
                var o = (object)ois.readObject();
                Server.subjects.add(o);
                System.out.println(o.index);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

class boardHandler extends Thread{
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public boardHandler(ObjectOutputStream oos, ObjectInputStream ois){
        this.oos = oos;
        this.ois = ois;
    }

    @Override
    public void run(){
        System.out.println("Added board");
        var input = "";
        var toReturn = "";
        while(true){
            try {
                input = ois.readUTF();
                if(input.startsWith("get")){
                    var split = input.split(" ");
                    var get = split[0];
                    var courseId = split[1];
                    var activityId = split[2];
                    for(var i=0;i<Server.subjects.size();i++){
                        if(Server.subjects.get(i).course_id == Integer.parseInt(courseId) && Server.subjects.get(i).activity_id == Integer.parseInt(activityId)){
                            toReturn+="Student: " + Server.subjects.get(i).index + " Poeni: " + Server.subjects.get(i).points + "\n";
                        }
                    }
                    oos.writeUTF(toReturn);
                    oos.flush();
                    toReturn = "";

                }

            } catch (IOException e) {
                System.out.println("except");
                break;
               // e.printStackTrace();
            }
        }
    }
}
