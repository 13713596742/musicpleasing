package com.alsm.musicpleasing.util;


import com.alsm.musicpleasing.dao.Sqldao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
public class BeantoSql {
    public static Sqldao sqldao;
    public final static String createTableSqlFilePath="/static/sql/";
    public final static String[] basicType={"int","float","double","long","char","java.lang.String","java.util.Date"};
    public final static String[] typeSql={"int(11)","float","double","bigint(20)","char(1)","char(255)","datetime"};
    public static String sqlmodel="CREATE TABLE *table* ( id int(20) NOT NULL PRIMARY KEY AUTO_INCREMENT *field*)AUTO_INCREMENT=100001;";
    public static Set<String> tableNames=new HashSet<>();
    public static String toCreateTable(Class c,String contcatTableName){
        String[]classname=c.getName().split("\\.");
        String tableName=nameFormat(classname[classname.length-1]);
        Field[] fields=c.getDeclaredFields();
        StringBuilder fieldsql=new StringBuilder();
        for(Field field:fields){
            StringBuilder fieldString=new StringBuilder();
            String fieldType=field.getType().getName();
            String typesql=null;
            for(int i=0;i<basicType.length;i++){
                if(fieldType.equals(basicType[i])){
                    typesql=typeSql[i];
                }
            }
            if(typesql!=null){
                fieldString.append(" ,");
                fieldString.append(nameFormat(field.getName()));
                fieldString.append(" ");
                fieldString.append(typesql);
            }else{
                if(fieldType.equals("java.util.List")){
                    Type type=((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                    checkTable(type.getTypeName(),tableName);


                }else if(fieldType.equals("java.util.Map")){
                    Type type=((ParameterizedType)field.getGenericType()).getActualTypeArguments()[1];
                    checkTable(type.getTypeName(),tableName);
                }else{
                    String fieldTable=nameFormat(field.getType().getName());
                    if(!tableNames.contains(fieldTable)){
                        toCreateTable(field.getType(),null);
                    }
                    fieldString.append(" ,");
                    fieldString.append(fieldTable+"_id");
                }
            }
            if(contcatTableName!=null){
                fieldsql.append(contcatTableName+" int(11) ");
            }
            fieldsql.append(fieldString.toString());
        }
        String sql=new String(sqlmodel);
        sql=sql.replaceAll("\\*table\\*",tableName);
        sql=sql.replaceAll("\\*field\\*",fieldsql.toString());
        /*File file=new File(createTableSqlFilePath+className+".sql");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        tableNames.add(tableName);
        System.out.println(sql);
        sqldao.creatTable("drop table if exists "+tableName);
        sqldao.creatTable(sql);


        return sql;
    }

    public static String nameFormat(String name){
        StringBuilder namebuilder=new StringBuilder(name);
        if(name!=null)
        for(int i=0;i<namebuilder.length();i++){
            if(namebuilder.charAt(i)<97){
                namebuilder.insert(i,"_");
                i++;
            }
        }
        if(namebuilder.charAt(0)=='_')  namebuilder.deleteCharAt(0);
        String nameTarget=namebuilder.toString().toLowerCase();
        return  nameTarget;
    }

    public static void checkTable(String tableName,String contcatTableName){
        if(!tableNames.contains(tableName)){
            try {
                toCreateTable(Class.forName(tableName),contcatTableName);
                System.out.println(tableName+"====tablename====");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void init(Sqldao sqlDao){
        sqldao=sqlDao;
    }
}
