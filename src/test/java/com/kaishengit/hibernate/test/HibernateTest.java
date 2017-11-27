package com.kaishengit.hibernate.test;

import com.kaishengit.hibernate.pojo.Book;
import com.kaishengit.hibernate.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class HibernateTest {


    Session session;
    SessionFactory sessionFactory;

    public Session getAnthorSession(){
         return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @Before
    public void getSession(){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    @After
    public void after() {
        session.getTransaction().commit();
    }

    @Test
    public void save(){
        //1.读取classpath中名称为hibernate.configuration.xml的配置文件
        Configuration configuration = new Configuration().configure();
        //2.创建SessionFactory
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //3.创建Session
        Session session = sessionFactory.getCurrentSession();
        //4.创建事务
        Transaction transaction = session.beginTransaction();
        //5.执行操作
        Book book = new Book();
        book.setBookAuthor("江帆");
        book.setBookName("语尽");
        book.setBookType("小说");
        session.save(book);
        //6.提交或者回滚事务
        transaction.commit();
    }
    @Test
    public void findById(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Book book = (Book) session.get(Book.class,1);
        System.out.println(book);
        session.getTransaction().commit();
    }
    @Test
    public void update(){
        session.getTransaction().begin();
        Book book = (Book) session.get(Book.class,1);
        book.setBookName("梦春秋");
        session.getTransaction().commit();
    }
    @Test
    public void delete(){
        session.getTransaction().begin();
        //删除时先进行查询再进行删除
        Book book = (Book) session.get(Book.class,1);
        session.delete(book);
        session.getTransaction().commit();
    }
    @Test
    public void save1(){
        session.getTransaction().begin();
        Book book = new Book();
        book.setBookName("江郎才尽");
        book.setBookType("叙事");
        book.setBookAuthor("一");
        session.save(book);
        session.getTransaction().commit();
        Session session1 = getAnthorSession();
        session1.getTransaction().begin();
        book.setBookType("纪实");
        session1.update(book);
        session1.getTransaction().commit();
    }
    @Test
    public void findAll(){
        session.beginTransaction();
        //HQL
        String hql = "from Book";
        Query query = session.createQuery(hql);
        List<Book> books = query.list();
        for(Book book : books) {
            System.out.println(book);
        }
        session.getTransaction().commit();
    }
    @Test
    public void findAllName(){
        session.beginTransaction();
        String hql = "select bookName from Book";
        Query query = session.createQuery(hql);
        List<String> names = query.list();
        for(String name : names){
            System.out.println(name);
        }
        session.getTransaction().commit();
    }
    @Test
    public void findAllNameAndAuthor(){
        session.beginTransaction();
        String hql = "select bookName, bookAuthor from Book";
        Query query = session.createQuery(hql);
        List<Object[]> lists = query.list();
        for(Object[] message : lists){
            System.out.println("bookName --->"+message[0]+"bookAuthor --->"+message[1]);
        }
    }
    @Test
    public void findCountOfAll(){
        session.beginTransaction();
        String hql = "select count(bookName) from Book where bookName = '语尽'";
        Query query = session.createQuery(hql);
        List<Object> messages = query.list();
        for(Object message : messages){
            System.out.println("count --->"+message);
        }

    }
    @Test
    public void limit(){
        session.beginTransaction();
        String hql = "from Book";
        Query query = session.createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(5);
        List<Book> books = query.list();
        for(Book book : books){
            System.out.println(book);
        }
    }
    @Test
    public void findByEverything(){
        session.beginTransaction();
        String hql = "from Book where bookAuthor = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,"一");
        List<Book> books = query.list();
//        query.uniqueResult()  当确定返回对象只有一个时使用
        for(Book book : books){
            System.out.println(books);
        }
    }
    @Test
    public void findByEverything1(){
        session.beginTransaction();
        String hql = "from Book where bookAuthor = :author";
        Query query = session.createQuery(hql);

        query.setString("author","一");
        List<Book> books = query.list();
        for(Book book : books){
            System.out.println(book);
        }
    }
}
















