//package com.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.stereotype.Repository;
//
//import com.bean.User;
//
////@Component
//@Repository
//public class UserDaoImpl implements IUserDao
//{
//    
//    @Autowired
//    private NamedParameterJdbcTemplate jdbcTemplate;
//    
//    @Override
//    public User findUser(String username, String password)
//    {
//        try
//        {
//            String sql = "select u.pk_id as id , u.user_name as username, u.password as password from t_user u where u.user_name = :username and   u.password = :password";
//            
//            SqlParameterSource paramSource = new BeanPropertySqlParameterSource(new User("2", username, password));
//            
//            RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
//            
//            User user = jdbcTemplate.queryForObject(sql, paramSource, rowMapper);
//            
//            // PreparedStatementCallback action = new
//            // PreparedStatementCallback<T>()
//            // {
//            //
//            // @Override
//            // public T doInPreparedStatement(PreparedStatement ps) throws
//            // SQLException, DataAccessException
//            // {
//            // // TODO Auto-generated method stub
//            // return ps.execute();
//            // }
//            // };
//            //
//            // jdbcTemplate.execute(sql, paramSource, action );
//            
//            return user;
//        }
//        catch (EmptyResultDataAccessException e)
//        {
//            return null;
//        }
//        
//    }
//    
//}
