package com.redis.demo.service;

import com.redis.demo.bean.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaApi
{
    public static void main(String[] args)
    {
        //相当于SessionFactory
        EntityManagerFactory emfactory = Persistence.
                createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.
                createEntityManager( );
        CriteriaBuilder criteriaBuilder = entitymanager
                .getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder
                .createQuery();
        Root<Employee> from = criteriaQuery.from(Employee.class);

        //select all records
        System.out.println("Select all records");
        CriteriaQuery<Object> select =criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = entitymanager
                .createQuery(select);
        List<Object> resultlist= typedQuery.getResultList();

        for(Object o:resultlist)
        {
            Employee e=(Employee)o;
            System.out.println("EID : "+e.getEid()
                    +" Ename : "+e.getEname());
        }

        //Ordering the records
        System.out.println("Select all records by follow ordering");
        CriteriaQuery<Object> select1 = criteriaQuery.select(from);
        select1.orderBy(criteriaBuilder.asc(from.get("ename")));
        TypedQuery<Object> typedQuery1 = entitymanager
                .createQuery(select);
        List<Object> resultlist1= typedQuery1.getResultList();

        for(Object o:resultlist1)
        {
            Employee e=(Employee)o;
            System.out.println("EID : "+e.getEid()
                    +" Ename : "+e.getEname());
        }

        entitymanager.close( );
        emfactory.close( );
    }
}
//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/jpa/jpa_criteria_api.html

