//package com.dao;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.StringUtils;
//
//import com.bean.Student;
//import com.vo.LogUtils;
//import com.vo.Page;
//import com.vo.StudentDTO;
//
////@Component
//@Repository
//public class StudentDaoImpl implements IStudentDao
//{
//    @Autowired
//    private NamedParameterJdbcTemplate jdbcTemplate;
//    
//    @Override
//    public Page<Student> findStudentByPage(Page<Student> page, StudentDTO condition)
//    {
//        
//        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(condition);
//        
//        String conditionSql = buildConditionSql(condition);
//        
//        String sql = "SELECT s.pk_student_id as id , s.age as age , s.create_time as creatTime , s.gender as gender , s.last_modify_time as lastModifyTime , s.student_name as name FROM t_student s WHERE 1 = 1 "
//                + conditionSql + " ORDER BY s.create_time DESC LIMIT " + page.getOffset() + "," + page.getPageSize();
//        
//        String countSql = "SELECT COUNT(s.pk_student_id) FROM t_student s WHERE 1 = 1 " + conditionSql;
//        
//        LogUtils.debug(sql);
//        LogUtils.debug(countSql);
//        
//        List<Student> pageList = jdbcTemplate.query(sql, paramSource,
//                new BeanPropertyRowMapper<Student>(Student.class));
//        
//        Long maxCount = jdbcTemplate.queryForObject(countSql, paramSource, Long.class);
//        
//        page.setPageList(pageList);
//        page.setMaxCount(maxCount);
//        
//        return page;
//    }
//    
//    @Override
//    public Student findeStudentById(String stuId)
//    {
//        String sql = "SELECT s.pk_student_id as id , s.age as age , s.create_time as creatTime , s.gender as gender , s.last_modify_time as lastModifyTime , s.student_name as name FROM t_student s WHERE 1 = 1 and s.pk_student_id = :stuId";
//        
//        LogUtils.debug(sql);
//        
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("stuId", stuId);
//        
//        Student stu = jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<Student>(Student.class));
//        
//        return stu;
//    }
//    
//    @Override
//    public void addStudent(Student stu)
//    {
//        String sql = " INSERT INTO t_student (pk_student_id,age,create_time,gender,last_modify_time,student_name,fk_class_id,fk_id_card ) VALUES "
//                + "(:id , :age , :createTime , :gender , :lastModifyTime, :name , NULL, NULL )";
//        
//        LogUtils.debug(sql);
//        
//        update(sql, stu);
//    }
//    
//    @Override
//    public void updateStudent(Student stu)
//    {
//        String sql = "UPDATE t_student s SET age = :age, last_modify_time = :lastModifyTime , student_name = :name , fk_class_id = NULL , fk_id_card = NULL "
//                + " WHERE s.pk_student_id = :id";
//        
//        LogUtils.debug(sql);
//        
//        update(sql, stu);
//    }
//    
//    @Override
//    public void deleteStudent(String stuId)
//    {
//        String sql = "DELETE s FROM t_student s where s.pk_student_id = :id ";
//        
//        LogUtils.debug(sql);
//        
//        update(sql, new Student(stuId, null, null, null));
//    }
//    
//    private void update(String sql, Student stu)
//    {
//        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(stu);
//        jdbcTemplate.update(sql, paramSource);
//    }
//    
//    private String buildConditionSql(StudentDTO condition)
//    {
//        String sql = "";
//        
//        if (!StringUtils.isEmpty(condition.getName()))
//        {
//            sql += " and s.student_name LIKE '%' :name '%' ";
//        }
//        
//        if (null != condition.getAge())
//        {
//            
//            sql += " and s.age = :age ";
//        }
//        
//        if (null != condition.getGender())
//        {
//            sql += " and s.gender = :gender ";
//        }
//        
//        return sql;
//    }
//    
//}
