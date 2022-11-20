package Learning.SocketProgramming.Ispitni.junizadaca;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Board{
    static int port = 5565;
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        Socket s = new Socket(address,port);
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        os.writeUTF("Board");
        os.flush();
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());

        Thread t = new Thread(new bHandler(s,os,is));
        t.start();

        os.flush();
    }
}
class bHandler extends Thread{
    Socket s;
    ObjectOutputStream os;
    ObjectInputStream is;

    public bHandler(Socket s, ObjectOutputStream os, ObjectInputStream is){
        this.s = s;
        this.os = os;
        this.is = is;
    }

    @Override
    public void run(){
        System.out.println("Connected");
        Scanner s = new Scanner(System.in);
        var received = "";
        while (true){
            var input = s.nextLine();
            //var split = input.split(" ");
            //var courseId = split[0];
            //var activityId = split[1];
            if(input.startsWith("get")){
                try {
                    os.writeUTF(input);
                    os.flush();
                    //System.out.println(is.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                received = is.readUTF();
                System.out.println(received);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}