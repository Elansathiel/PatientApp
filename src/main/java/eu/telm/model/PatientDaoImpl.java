package eu.telm.model;

import eu.telm.dataBase.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kasia on 04.01.17.
 */
@Repository
public class PatientDaoImpl implements PatientDao {

    SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Patient> findByNazwisko(String nazwisko) {
        return null;
    }

    @Override
    public List<Patient> findByNazwiskoStartsWithIgnoreCase(String nazwisko) {
        Session session = this.sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Patient.class);
        criteria.add(Restrictions.ilike("nazwisko",nazwisko, MatchMode.START));
        List<Patient> patients = criteria.list();
        for(Patient patient : patients){
            System.out.println("Patient:\t"+patient.getImie()+"\t"+patient.getNazwisko()+"\t"+patient.getPesel());
        }
        session.close();
        return patients;
    }

    @Override
    public List<Patient> getAll() {
        Session session = this.sessionFactory.openSession();
        List<Patient> patients = session.createQuery("from Patient").list();
        for(Patient patient : patients) {
            System.out.println("Imie\t" + patient.getImie() + " nazwisko\t");
            if(patient.getRealizacjeList().size()>0)
                System.out.println("badanie\t"+patient.getRealizacjeList().get(0).getOperacja().getNazwa());
        }
        System.out.println("Robić" + patients.size());
        session.close();
        return patients;
    }

    @Override
    public Long save(Patient e) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.save(e);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully created " + e.toString());
        return e.getId();
    }



}
