package Learning.SocketProgramming.Ispitni.junizadaca;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Teacher {
    static int port = 5565;
    public static void main(String[] args) throws UnknownHostException {
        Socket s;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        InetAddress address = InetAddress.getByName("localhost");
        Scanner scanner = new Scanner(System.in);
        DataOutputStream dos;

        try {
            s = new Socket(address,port);
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            oos.writeUTF("Teacher");
            oos.flush();


            tHandler th = new tHandler(s,oos,ois);
            Thread t = new Thread(th);
            t.start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
class tHandler extends Thread{
    Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public tHandler(Socket s, ObjectOutputStream oos, ObjectInputStream ois){
        this.s = s;
        this.oos = oos;
        this.ois = ois;
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        try {
            while(true){
                var input = scanner.nextLine();
                String[] objString = input.split(" ");
                object o = new object(Integer.parseInt(objString[0]),Integer.parseInt(objString[1]),
                        Integer.parseInt(objString[2]),Integer.parseInt(objString[3]));
                System.out.println(o.index);
                oos.writeObject(o);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}