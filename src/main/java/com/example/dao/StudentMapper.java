package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.model.StudentModel;

@Mapper
public interface StudentMapper
{
    @Select("select npm, name, gpa from student where npm = #{npm}")
    StudentModel selectStudent (@Param("npm") String npm);

    @Select("select npm, name, gpa from student")
    List<StudentModel> selectAllStudents ();

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (StudentModel student);

    @Delete("DELETE FROM student where npm = #{npm}")
    void deleteStudent(@Param("npm") String npm);

    @Update("Update student set name = #{name}, gpa = #{gpa} where npm = #{npm}")
    void updateStudent(@Param("npm") String npm, @Param("name") String name, @Param("gpa") double gpa);

}
