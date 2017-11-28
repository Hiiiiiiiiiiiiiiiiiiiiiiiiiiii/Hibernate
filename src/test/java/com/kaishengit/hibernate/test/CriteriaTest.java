package com.kaishengit.hibernate.test;

import com.kaishengit.hibernate.pojo.School;
import com.kaishengit.hibernate.util.HibernateUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CriteriaTest {
    Session session;
    @Before
    public void getSession(){
        session = HibernateUtil.getSession();
    }

    //查询所有
    @Test
    public void findAll(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        List<School> schools = criteria.list();
        for(School school : schools){
            System.out.println(school);
        }
        session.getTransaction().commit();
    }
    //筛选条件是一个的时候
    @Test
    public void whereOne(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        criteria.add(Restrictions.eq("schoolName","断罪小学"));
        School school = (School) criteria.uniqueResult();
        System.out.println(school);
        session.getTransaction().commit();
    }
    //筛选条件是多个的时候
    @Test
    public void whereMany(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        criteria.add(Restrictions.like("schoolName","小学",MatchMode.END));//%小学 START 小学%  END  小学 则不需要加第三个参数   %小学% 使用ANYWHERE
        //当需要设置多个筛选项的时候
//        criteria.add(Restrictions.eq("id",1));
//        criteria.add(Restrictions.or(Restrictions.eq("id",1),Restrictions.eq("id",2))); 或者
        List<School> schools = criteria.list();
        for(School school : schools){
            System.out.println(school);
        }
        session.getTransaction().commit();
    }
    //分页
    @Test
    public void limit(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(5);
        List<School> schools = criteria.list();
        for(School school : schools){
            System.out.println(school);
        }
        session.getTransaction().commit();
    }
    //聚合函数
    @Test
    public void count(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        criteria.add(Restrictions.eq("id",1));
        //当查询结果有多个的时候可以用Object数组来接收
        criteria.setProjection(Projections.count("schoolName"));
        Object obj = criteria.uniqueResult();
        System.out.println(obj);
        session.getTransaction().commit();
    }
    //排序
    @Test
    public void order(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        //desc倒序  asc正序
        criteria.addOrder(Order.desc("id"));
        List<School> schools = criteria.list();
        for(School school : schools){
            System.out.println(school);
        }
        session.getTransaction().commit();
    }
    //当筛选条件是多个的时候(略麻烦)
    @Test
    public void and(){
        session.beginTransaction();
        Criteria criteria = session.createCriteria(School.class);
        Criterion schoolNameCriterion = Restrictions.eq("schoolName","吃鸡小学");
        Criterion schoolIdCriterion = Restrictions.eq("id",3);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(schoolIdCriterion);
        disjunction.add(schoolNameCriterion);
        criteria.add(disjunction);
        School school = (School) criteria.uniqueResult();
        System.out.println(school);
        session.getTransaction().commit();
    }
}
