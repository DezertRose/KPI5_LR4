package com.mycompany.app.graphQLResolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;


import com.mycompany.app.DatabaseConection;
import com.mycompany.app.Facade;
import com.mycompany.app.Hendler.CrudData;
import com.mycompany.app.Hendler.Support;
import com.mycompany.app.Objects.Hostel;
import com.mycompany.app.Objects.Students;
import com.mycompany.app.Objects.User;
import com.mycompany.app.ToBuild.Data;
import com.mycompany.app.ToBuild.hostelBuild;
import com.mycompany.app.ToBuild.studentsBuild;
import com.mycompany.app.ToBuild.userBuild;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mycompany.app.Facade.firstKesh;

public class Query implements GraphQLQueryResolver {

    public ArrayList<Students> getAllStudents(){
        ArrayList<Students> allData = new ArrayList<>(Facade.allStudents);
        try{
            if(Facade.firstKesh == null){
                Facade.firstKesh = new ArrayList<>();
                try{
                    URL url = new URL("http://localhost:5001/students");

                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setRequestMethod("GET");

                    StringBuilder data = new StringBuilder();
                    try(BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()))){
                        String tempLine;
                        while((tempLine = input.readLine()) != null){
                            data.append(tempLine);
                        }
                    }
                    ArrayList<Object> strArr = Support.getArrFromJson(data.toString());
                    ArrayList<Map<String, Object>> mso = new ArrayList<>();
                    strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                    for (Map<String, Object> s : mso) {
                        hostelBuild hstB = new hostelBuild();
                        studentsBuild stdB = new studentsBuild();
                        Data director = new Data(hstB);
                        Hostel host = new Hostel();

                        Map<String, Object> hostMap = (Map<String, Object>) s.get("hostel");
                        if (hostMap != null){
                            director.insideData.id = (int)hostMap.get("id");
                            director.insideData.name = (String)hostMap.get("name");
                            director.insideData.street = (String)hostMap.get("street");
                            director.insideData.numbOfRooms = (int)hostMap.get("numb_of_room");
                            director.insideData.state = (String)hostMap.get("state");
                            director.insideData.faculty = (String)hostMap.get("faculty");
                            director.insert("hostel");
                            host = hstB.getResult();
                        }
                        director.changeBuilder(stdB);

                        director.insideData.id = (int) s.get("id");
                        director.insideData.letters = (String) s.get("letters");
                        director.insideData.numbOfGroup = (int)s.get("numb_of_group");
                        director.insideData.faculty =(String) s.get("faculty");
                        director.insideData.priv = (int)s.get("privilege");
                        director.insideData.state = (String) s.get("state");
                        director.insideData.date = (String) s.get("date");
                        director.insideData.hostel = host;

                        Students student = new Students();
                        director.insert("");
                        student = stdB.getResult();
                        Facade.firstKesh.add(student);
                    }
                }
                catch (Exception ex){ex.printStackTrace();}
            }
            if (Facade.firstKesh != null){
                allData.addAll(Facade.firstKesh);
            }

            if (Facade.secondKesh == null){
                Facade.secondKesh = new ArrayList<>();

                try{
                    for (int i=1; i<11; i++){
                        URL url = new URL("http://localhost:5003/price-list?p="+i);

                        HttpURLConnection conect = (HttpURLConnection)url.openConnection();
                        conect.setRequestMethod("GET");

                        StringBuilder strBuild = new StringBuilder();
                        try(BufferedReader in = new BufferedReader(new InputStreamReader(conect.getInputStream()))) {
                            String line;
                            while ((line = in.readLine()) != null){
                                strBuild.append(line);
                            }
                        }

                        ArrayList<Object> strArr = Support.getArrFromJson(strBuild.toString());
                        ArrayList<Map<String, Object>> mso = new ArrayList<>();
                        strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                        for (Map<String, Object> s : mso) {
                            hostelBuild hstB = new hostelBuild();
                            studentsBuild stdB = new studentsBuild();
                            Data director = new Data(hstB);
                            Hostel host = new Hostel();

                            Map<String, Object> hostMap = (Map<String, Object>) s.get("hostel");
                            if (hostMap != null){
                                director.insideData.id = (int)hostMap.get("id");
                                director.insideData.name = (String)hostMap.get("name");
                                director.insideData.street = (String)hostMap.get("street");
                                director.insideData.numbOfRooms = (int)hostMap.get("numb_of_room");
                                director.insideData.state = (String)hostMap.get("state");
                                director.insideData.faculty = (String)hostMap.get("faculty");
                                director.insert("hostel");
                                host = hstB.getResult();
                            }
                            director.changeBuilder(stdB);

                            director.insideData.id = (int) s.get("id");
                            director.insideData.letters = (String) s.get("letters");
                            director.insideData.numbOfGroup = (int)s.get("numb_of_group");
                            director.insideData.faculty =(String) s.get("faculty");
                            director.insideData.priv = (int)s.get("privilege");
                            director.insideData.state = (String) s.get("state");
                            director.insideData.date = (String) s.get("date");
                            director.insideData.hostel = host;

                            Students student = new Students();
                            director.insert("");
                            student = stdB.getResult();
                            Facade.secondKesh.add(student);
                        }
                    }
                }catch (Exception ex){ex.printStackTrace();}
            }

            if (Facade.secondKesh != null){
                allData.addAll(Facade.secondKesh);
            }
            DatabaseConection.closeConnection();
            return allData;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return allData;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> allData = new ArrayList<>(Facade.allUsers);
        try{
                try{
                    URL url = new URL("http://localhost:5001/users");

                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setRequestMethod("GET");

                    StringBuilder data = new StringBuilder();
                    try(BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()))){
                        String tempLine;
                        while((tempLine = input.readLine()) != null){
                            data.append(tempLine);
                        }
                    }
                    ArrayList<Object> strArr = Support.getArrFromJson(data.toString());
                    ArrayList<Map<String, Object>> mso = new ArrayList<>();
                    strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                    for (Map<String, Object> s : mso) {
                        hostelBuild hstB = new hostelBuild();
                        userBuild stdB = new userBuild();
                        Data director = new Data(hstB);
                        Hostel host = new Hostel();

                        Map<String, Object> hostMap = (Map<String, Object>) s.get("hostel");
                        if (hostMap != null){
                            director.insideData.id = (int)hostMap.get("id");
                            director.insideData.name = (String)hostMap.get("name");
                            director.insideData.street = (String)hostMap.get("street");
                            director.insideData.numbOfRooms = (int)hostMap.get("numb_of_room");
                            director.insideData.state = (String)hostMap.get("state");
                            director.insideData.faculty = (String)hostMap.get("faculty");
                            director.insert("hostel");
                            host = hstB.getResult();
                        }
                        director.changeBuilder(stdB);

                        director.insideData.id = (int) s.get("id");
                        director.insideData.mail = (String) s.get("mail");
                        director.insideData.password = (String)s.get("password");
                        director.insideData.faculty =(String) s.get("faculty");
                        director.insideData.hostel = host;

                        User student = new User();
                        director.insert("");
                        student = stdB.getResult();
                        allData.add(student);
                    }
                }
                catch (Exception ex){ex.printStackTrace();}

                try{
                    for (int i=1; i<11; i++){
                        URL url = new URL("http://localhost:5003/price-list?p="+i);

                        HttpURLConnection conect = (HttpURLConnection)url.openConnection();
                        conect.setRequestMethod("GET");

                        StringBuilder strBuild = new StringBuilder();
                        try(BufferedReader in = new BufferedReader(new InputStreamReader(conect.getInputStream()))) {
                            String line;
                            while ((line = in.readLine()) != null){
                                strBuild.append(line);
                            }
                        }

                        ArrayList<Object> strArr = Support.getArrFromJson(strBuild.toString());
                        ArrayList<Map<String, Object>> mso = new ArrayList<>();
                        strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                        for (Map<String, Object> s : mso) {
                            hostelBuild hstB = new hostelBuild();
                            userBuild stdB = new userBuild();
                            Data director = new Data(hstB);
                            Hostel host = new Hostel();

                            Map<String, Object> hostMap = (Map<String, Object>) s.get("hostel");
                            if (hostMap != null){
                                director.insideData.id = (int)hostMap.get("id");
                                director.insideData.name = (String)hostMap.get("name");
                                director.insideData.street = (String)hostMap.get("street");
                                director.insideData.numbOfRooms = (int)hostMap.get("numb_of_room");
                                director.insideData.state = (String)hostMap.get("state");
                                director.insideData.faculty = (String)hostMap.get("faculty");
                                director.insert("hostel");
                                host = hstB.getResult();
                            }
                            director.changeBuilder(stdB);

                            director.insideData.id = (int) s.get("id");
                            director.insideData.mail = (String) s.get("mail");
                            director.insideData.password = (String)s.get("password");
                            director.insideData.faculty =(String) s.get("faculty");
                            director.insideData.hostel = host;

                            User student = new User();
                            director.insert("");
                            student = stdB.getResult();
                            allData.add(student);
                        }
                    }
                }catch (Exception ex){ex.printStackTrace();}
            DatabaseConection.closeConnection();
            return allData;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return allData;
    }


    public ArrayList<Hostel> getAllHostels(){
        ArrayList<Hostel> allData = new ArrayList<>(Facade.allHostels);
        try{
            try{
                URL url = new URL("http://localhost:5001/users");

                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                StringBuilder data = new StringBuilder();
                try(BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()))){
                    String tempLine;
                    while((tempLine = input.readLine()) != null){
                        data.append(tempLine);
                    }
                }
                ArrayList<Object> strArr = Support.getArrFromJson(data.toString());
                ArrayList<Map<String, Object>> mso = new ArrayList<>();
                strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                for (Map<String, Object> s : mso) {
                    hostelBuild hstB = new hostelBuild();;
                    Data director = new Data(hstB);
                    Hostel host = new Hostel();

                    director.insideData.id = (int)s.get("id");
                    director.insideData.name = (String)s.get("name");
                    director.insideData.street = (String)s.get("street");
                    director.insideData.numbOfRooms = (int)s.get("numb_of_room");
                    director.insideData.state = (String)s.get("state");
                    director.insideData.faculty = (String)s.get("faculty");
                    director.insert("hostel");
                    host = hstB.getResult();

                    allData.add(host);
                }
            }
            catch (Exception ex){ex.printStackTrace();}

            try{
                for (int i=1; i<11; i++){
                    URL url = new URL("http://localhost:5003/price-list?p="+i);

                    HttpURLConnection conect = (HttpURLConnection)url.openConnection();
                    conect.setRequestMethod("GET");

                    StringBuilder strBuild = new StringBuilder();
                    try(BufferedReader in = new BufferedReader(new InputStreamReader(conect.getInputStream()))) {
                        String line;
                        while ((line = in.readLine()) != null){
                            strBuild.append(line);
                        }
                    }

                    ArrayList<Object> strArr = Support.getArrFromJson(strBuild.toString());
                    ArrayList<Map<String, Object>> mso = new ArrayList<>();
                    strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                    for (Map<String, Object> s : mso) {
                        hostelBuild hstB = new hostelBuild();;
                        Data director = new Data(hstB);
                        Hostel host = new Hostel();

                        director.insideData.id = (int)s.get("id");
                        director.insideData.name = (String)s.get("name");
                        director.insideData.street = (String)s.get("street");
                        director.insideData.numbOfRooms = (int)s.get("numb_of_room");
                        director.insideData.state = (String)s.get("state");
                        director.insideData.faculty = (String)s.get("faculty");
                        director.insert("hostel");
                        host = hstB.getResult();

                        allData.add(host);
                    }
                }
            }catch (Exception ex){ex.printStackTrace();}
            DatabaseConection.closeConnection();
            return allData;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return allData;
    }

    /*fun request(id:Int): Request? {
        return servicesFacade.getDetail(id)
    }
    fun requests():List<Request> {
        return servicesFacade.getAllRequests()
    }
    fun vacancy(id:Int): Vacancy?{
        val request = servicesFacade.getDetail(id)
        return if (request != null) {
            Vacancy(request.VacancyID, request.VacancyInfo, request.Salary, request.CompanyID)
        }
        else null
    }
    fun client(id:Int): Client?{
        val request = servicesFacade.getDetail(id)
        return if (request != null) {
            Client(request.ClientID, request.ClientInfo)
        }
        else null
    }
    fun vacancies():List<Vacancy>
    {
        val requests = servicesFacade.getAllRequests()
        return requests.map {
            Vacancy(it.VacancyID, it.VacancyInfo, it.Salary, it.CompanyID)
        }.distinct()
    }
    fun clients():List<Client>
    {
        val requests = servicesFacade.getAllRequests()
        return requests.map {
            Client(it.ClientID, it.ClientInfo)
        }.distinct()
    }*/

}