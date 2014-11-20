package models;

import com.avaje.ebean.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Asignature extends Model {
    @EmbeddedId
    public AsignaturePK id;
    @ManyToOne
    @JoinColumn(name="professor_id",insertable=false,updatable=false)
    public Professor professor;
    @ManyToOne
    @JoinColumn(name="course_id",insertable=false,updatable=false)
    public Course course;
    @ManyToOne
    @JoinColumn(name="section_id",insertable=false,updatable=false)
    public Section section;

    public Asignature(int professor_id,int course_id,int section_id){
        id=new AsignaturePK(professor_id,course_id,section_id);
        professor=Professor.find.byId(professor_id);
        course=Course.find.byId(course_id);
        section=Section.find.byId(section_id);
    }
    public static Asignature create(int professor_id,int course_id,int section_id) {
        Asignature asignature = new Asignature(professor_id,course_id,section_id);
        asignature.save();
        return asignature;
    }
    public Map getMap(){
        Map<String,Map<String,String>> map=new HashMap<>();
        map.put("professor",professor.getMap());
        map.put("course",course.getMap());
        map.put("section",section.getMap());
        return map;
    }
    @Embeddable
    public static class AsignaturePK{
        public int professor_id;
        public int course_id;
        public int section_id;
        public AsignaturePK(int professor_id,int course_id,int section_id){
            this.professor_id=professor_id;
            this.course_id=course_id;
            this.section_id=section_id;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AsignaturePK that = (AsignaturePK) o;

            if (course_id != that.course_id) return false;
            if (professor_id != that.professor_id) return false;
            if (section_id != that.section_id) return false;

            return true;
        }
        @Override
        public int hashCode() {
            int result = professor_id;
            result = 31 * result + course_id;
            result = 31 * result + section_id;
            return result;
        }
    }
    public static Finder<AsignaturePK,Asignature> find = new Finder<>(AsignaturePK.class,Asignature.class);
    public static Asignature findById(int professor_id,int course_id,int section_id){
        return find.byId(new AsignaturePK(professor_id,course_id,section_id));
    }
}
