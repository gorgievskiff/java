package Learning.SocketProgramming.Ispitni.junizadaca;

import java.io.Serializable;

public class object implements Serializable {
    int index;
    int activity_id;
    int points;
    int course_id;

    public object(int index, int activity_id, int points,int course_id){
        this.index = index;
        this.course_id = course_id;
        this.activity_id = activity_id;
        this.points = points;
    }
}
