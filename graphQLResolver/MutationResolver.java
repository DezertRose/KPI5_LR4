package com.mycompany.app.graphQLResolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import com.mycompany.app.DatabaseConection;
import com.mycompany.app.Facade;

import com.mycompany.app.Objects.*;
import com.mycompany.app.Request;
import com.mycompany.app.ToBuild.*;


public class MutationResolver implements GraphQLMutationResolver {

    public User createNewUser(String faculty,Integer hostel_id, String mail, String password){
        userBuild ub = new userBuild();
        Data bData = new Data(ub);

        bData.insideData.faculty = faculty;
        bData.insideData.hostel = Facade.HostelsData.getHostelById(hostel_id, Facade.allHostels);
        bData.insideData.mail = mail;
        bData.insideData.password = password;
        bData.insert("user");
        User newUser = ub.getResult();

        //Facade.allUsers.add(newUser);
        System.out.println(newUser);
        Request re = new Request("INSERT INTO FACULTY (name)\n" +
                "SELECT * \n" +
                "FROM (SELECT '"+newUser.faculty+"' as temp) as temt\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT name FROM FACULTY WHERE name = '"+newUser.faculty+"'\n" +
                ")\n" +
                "insert into \"USER\"\n" +
                "values((select FACULTY_ID \n" +
                "\t\tfrom FACULTY \n" +
                "\t\twhere (FACULTY.NAME ='"+newUser.faculty+"')), "+newUser.hostel.id+", '"+newUser.mail+"', '"+newUser.password+"');");
        String response = (String)re.exec();
        Facade.allUsers = Facade.HostelsData.setUserData(Facade.allHostels);
        DatabaseConection.closeConnection();
        return newUser;
    }

    public String deleteStudents(Integer tempId){
        try{
            Request req = new Request("Delete from STUDENT where STUDENT_ID ="+tempId);

            for(int i =0; i<Facade.allStudents.size(); i++){
                if(tempId == Facade.allStudents.get(i).id){
                    Facade.allStudents.remove(Facade.allStudents.get(i));
                }
            }
            String response = (String)req.exec();
            return response;
        }
        catch (Exception ex){
            return ex.toString();
        }
    }

    public String updateHostel(String title, String street, Integer id){
        int tempId = id;
        String tempTitle = title;
        String tempStreet= street;

        Request req = new Request("  update HOSTEL\n" +
                "  set TITLE = '"+tempTitle+"',\n" +
                "\t  STREET = '"+tempStreet+"'\n" +
                "where HOSTEL_ID = "+tempId+";");
        String response = (String)req.exec();
        Facade.allHostels = Facade.HostelsData.setHostelData();
        DatabaseConection.closeConnection();
        return response;
    }





    //public Hostel updateNewHostel(){}

    

    /*public void addRequest(requestInfo:String, vacancyID:Int, clientID:Int):RequestForPost{
        val requestForPost=RequestForPost(requestInfo,vacancyID,clientID)
        mainServiceImpl.create(requestForPost)
        return requestForPost
    }
    fun updateRequest(id:Int,requestInfo: String):Int{
        mainServiceImpl.update(id,requestInfo)
        return id
    }
    fun deleteRequest(id:Int):Boolean{
        mainServiceImpl.deleteById(id)
        return true
    }*/

}