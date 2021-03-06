package com.unity.mynativeapp.POJO;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


//각 trashcan 정보를 임시저장하는 자바 객체
public class Trashcan implements Serializable, Comparable<Trashcan>
{
    private String tid;       //trashcan 고유 id
    private String creator;   // trashcan 생성 user id
    private String name;      //trashcan 이름
    private Double latitude;  //trashcan location : 위도
    private Double longitude; //trashcan location : 경도
    private Integer report;   //trashcan 신고 횟수
    private Integer distance; //trashcan 현재위치에서의 거리 (임시)

    public Trashcan() {

    }

    //Trashcan 객체 생성 시 매개변수 값을 객체에 저장
    public Trashcan(String tid, String creator, String name, Double latitude, Double longitude, Integer report) {
        this.tid = tid;
        this.creator = creator;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.report = report;
        this.distance = 0;
    }

    // 객체에 저장된 정보를 HashMap 형태로 저장
    @Exclude
    public Map<String, Object> TrashcantoMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("tid", tid);
        result.put("creator",creator);
        result.put("name", name);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("report", report);
        return result;
    }

    // 각 정보 get/set
    public String getTid() { return tid; }
    public void setTid(String tid) { this.tid = tid; }
    public String getCreator() {return creator;}
    public void setCreator(String creator) { this.creator = creator; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public int getReport() { return report; }
    public void setReport(int report) { this.report = report; }
    public int getDistance() {return distance; }
    public void setDistance(int distance) { this.distance = distance; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("tid");
        sb.append('=');
        sb.append(this.tid);
        sb.append(',');
        sb.append(System.getProperty("line.separator"));
        sb.append("creator");
        sb.append('=');
        sb.append(this.creator);
        sb.append(',');
        sb.append(System.getProperty("line.separator"));
        sb.append("name");
        sb.append('=');
        sb.append(this.name);
        sb.append(',');
        sb.append(System.getProperty("line.separator"));
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append(System.getProperty("line.separator"));
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append(System.getProperty("line.separator"));
        sb.append("report");
        sb.append('=');
        sb.append(((this.report == null)?"<null>":this.report));
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    
    // 정렬 메소드
    @Override
    public int compareTo(Trashcan t) {
        if (t.distance < distance) {
            return 1;
        } else if (t.distance > distance) {
            return -1;
        }
        return 0;
    }
}

