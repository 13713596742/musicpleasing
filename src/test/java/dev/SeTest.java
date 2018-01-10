package dev;

import com.alsm.musicpleasing.MusicpleasingApplication;
import com.alsm.musicpleasing.dao.Sqldao;
import com.alsm.musicpleasing.model.User;
import org.junit.Test;
import com.alsm.musicpleasing.util.BeantoSql;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MusicpleasingApplication.class)
public class SeTest {
    @Autowired
    private Sqldao sqldao;
    @Test
    public void pathTest(){
        File file=new File("/");
        for(File f:file.listFiles()){
        System.out.println(f.getName());}
    }

    @Test
    public void typeTest(){
        User user=new User("zwt","null","guangdong",22);
        try {
            Type genericType=user.getClass().getDeclaredField("bookid").getGenericType();
            for(Type t: ((ParameterizedType)genericType).getActualTypeArguments()){
                try {
                    Class c=Class.forName(t.getTypeName());
                    System.out.println(c);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void beantosqlTest(){
       // sqldao.creatTable("create table test (id int(11) primerykey");B
        BeantoSql.init(sqldao);
       BeantoSql.toCreateTable(User.class,null);
    }

}
